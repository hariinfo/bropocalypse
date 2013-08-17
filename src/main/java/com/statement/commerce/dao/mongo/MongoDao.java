package com.statement.commerce.dao.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.ReadPreference;
import com.mongodb.ReplicaSetStatus;
import com.mongodb.WriteConcern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import static com.mongodb.WriteConcern.REPLICAS_SAFE;
import static com.mongodb.WriteConcern.SAFE;

@Repository
public class MongoDao
{
  protected static final String      DOTS_IN_MAP_KEY_REPLACEMENT                      = "~~~~~";
  private String FSYNC_LOG                                 = "] Fsync [";
  private String ASSIGNMENT_OF_WRITE_CONCERN_W             = "] assignment of WriteConcern W [";
  private String ASSIGNMENT_TO_COLLECTION_LOG              = "] assignment to collection [";
  private String SETTING_READ_PREFERENCE_FOR_DATABASE      = "Setting ReadPreference for database [";
  private String SETTING_WRITE_CONCERN_FOR_DATABASE        = "Setting WriteConcern for database [";
  private String WRITE_CONCERN_DETERMINATION_LOG           = "] WriteConcern determination";
  private String COLLECTION_LOG                            = "] collection [";

  private static final Logger LOG                                              = LoggerFactory.getLogger(MongoDao.class);
  public static final int            DESCENDING_SORT_ORDER                            = -1;
  public static final String         NOT_IN_OPERATOR                                  = "$nin";
  private static final String        NULL_MONGODB_DATABASE_PREVENTING_COLLECTION      = "Null mongodb database preventing collection [";
  public static final String         READ_PREFERENCE_DETERMINATION_LOG                = "] ReadPreference determination";
  private static final String        NULL_MONGODB_DATABASE_PREVENTING_READ_PREFERENCE = "Null mongodb database preventing ReadPreference [";
  @Autowired
  protected MongoTemplate            mongoTemplate;  // Spring context into the world of MongoDB

  /** Initializes the default database-level WriteConcern and ReadPreference. */
  @PostConstruct
  public void init()
  {
    setReadPreferenceLevel(determineReadPreference());
    setWriteConcernLevel(determineWriteConcern());
    ((MappingMongoConverter) mongoTemplate.getConverter()).setMapKeyDotReplacement(DOTS_IN_MAP_KEY_REPLACEMENT);
  }

  /**
   * Gets current WriteConcern at the database level for write related database requests.
   *
   * @return  currently assigned WriteConcern level; null if the mongo database is inaccessible
   */
  public WriteConcern getWriteConcernLevel()
  {
    WriteConcern concern = null;

    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DB database = mongoTemplate.getDb();

      if (database != null)
      {
        concern = database.getWriteConcern();
      }
    }

    if (concern == null)
    {
      LOG.error("Null mongodb database preventing WriteConcern determination");
    }

    return concern;
  }

  /**
   * Gets current WriteConcern at the collection level for write related database requests.
   *
   * @param   targetCollection  source collection targeted
   *
   * @return  currently assigned WriteConcern level for targetCollection; null if the mongo database is inaccessible
   */
  public WriteConcern getWriteConcernLevel(String targetCollection)
  {
    WriteConcern concern = null;

    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DB database = mongoTemplate.getDb();

      if (database != null)
      {
        DBCollection collection = database.getCollection(targetCollection);

        if (collection != null)
        {
          concern = collection.getWriteConcern();
        }
      }
    }

    if (concern == null)
    {
      LOG.error(NULL_MONGODB_DATABASE_PREVENTING_COLLECTION + targetCollection + WRITE_CONCERN_DETERMINATION_LOG);
    }

    return concern;
  }

  /**
   * Sets current WriteConcern at the collection level for write related database requests.
   *
   * @param  targetCollection    target collection name to assign targetWriteConcern
   * @param  targetWriteConcern  target WriteConcern level to assign and use
   */
  public void setWriteConcernLevel(String targetCollection, WriteConcern targetWriteConcern)
  {
    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DBCollection collection = mongoTemplate.getCollection(targetCollection);

      if (collection != null)
      {
        collection.setWriteConcern(targetWriteConcern);

        String       databaseName = mongoTemplate.getDb().getName();
        WriteConcern concern      = collection.getWriteConcern();

        if (LOG.isDebugEnabled())
        {
          LOG.debug(SETTING_WRITE_CONCERN_FOR_DATABASE + databaseName + COLLECTION_LOG + targetCollection + "] W ["
              + concern.getWString() + "] J [" + concern.getJ() + FSYNC_LOG + concern.getFsync() + ']');
        }
      }
    }
    else
    {
      LOG.error(NULL_MONGODB_DATABASE_PREVENTING_COLLECTION + targetCollection + ASSIGNMENT_OF_WRITE_CONCERN_W
          + targetWriteConcern.getWString() + "] J [" + targetWriteConcern.getJ() + FSYNC_LOG + targetWriteConcern.getFsync()
          + ']');
    }
  }

  /**
   * Sets current WriteConcern at the database level for write related database requests.
   *
   * @param  targetWriteConcern  target WriteConcern level to assign and use
   */
  public void setWriteConcernLevel(WriteConcern targetWriteConcern)
  {
    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      mongoTemplate.setWriteConcern(targetWriteConcern);

      String       databaseName = mongoTemplate.getDb().getName();
      WriteConcern concern      = mongoTemplate.getDb().getWriteConcern();

      if (LOG.isDebugEnabled())
      {
        LOG.debug(SETTING_WRITE_CONCERN_FOR_DATABASE + databaseName + "] W [" + concern.getWString() + "] J [" + concern.getJ()
            + FSYNC_LOG + concern.getFsync() + ']');
      }
    }
    else
    {
      LOG.error("Null mongodb database preventing assignment of WriteConcern W [" + targetWriteConcern.getWString() + "] J ["
          + targetWriteConcern.getJ() + FSYNC_LOG + targetWriteConcern.getFsync() + ']');
    }
  }

  /**
   * Determines whether the current database is stand-alone or a replicaSet.
   *
   * @return  True when current database is a replicaSet, false otherwise.
   */
  public boolean isReplicaSet()
  {
    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DB               database         = mongoTemplate.getDb();
      ReplicaSetStatus replicaSetStatus = database.getMongo().getReplicaSetStatus();

      if (replicaSetStatus == null)
      {
        // if replicaSetStatus is missing/null then we are connecting to a stand-alone database, not a replicaSet
        return false;
      }
      else
      {
        // replicaSetStatus is allocated, so we are connected to a replicaSet
        return true;
      }
    }
    else
    {
      // determination cannot be made so default to no replicaSet
      return false;
    }
  }

  protected MongoTemplate getMongoTemplate()
  {
    return mongoTemplate;
  }

  /**
   * Sanity determination for appropriate WriteConcern level across all write related database requests. Inspects the database replicaSet status to
   * decide whether to use SAFE for single databases, or REPLICAS_SAFE for replicating databases. Defaults to SAFE if no other determination can be
   * made.
   *
   * @return  appropriate WriteConcern database level based on the existence (or not) of database replicaSet capability/status
   */
  private WriteConcern determineWriteConcern()
  {
    WriteConcern writeConcern;

    if (isReplicaSet())
    {
      // this is a replicaSet- using REPLICAS_SAFE concern level
      writeConcern = REPLICAS_SAFE;
    }
    else
    {
      // stand-alone database otherwise- using SAFE concern level
      writeConcern = SAFE;
    }

    return writeConcern;
  }

  /**
   * Gets current ReadPreference for read-only related database level requests.
   *
   * @return  currently assigned ReadPreference level
   */
  public ReadPreference getReadPreferenceLevel()
  {
    ReadPreference readPreference = null;

    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DB database = mongoTemplate.getDb();

      if (database != null)
      {
        readPreference = database.getReadPreference();
      }
    }

    if (readPreference == null)
    {
      LOG.error("Null mongodb database preventing ReadPreference determination");
    }

    return readPreference;
  }

  /**
   * Gets current ReadPreference for read-only related database level requests.
   *
   * @param   targetCollection  targeted collection for ReadPreference retrieval
   *
   * @return  currently assigned ReadPreference level
   */
  public ReadPreference getReadPreferenceLevel(String targetCollection)
  {
    ReadPreference readPreference = null;

    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DB database = mongoTemplate.getDb();

      if (database != null)
      {
        DBCollection collection = database.getCollection(targetCollection);

        if (collection != null)
        {
          readPreference = collection.getReadPreference();
        }
      }
    }

    if (readPreference == null)
    {
      LOG.error(NULL_MONGODB_DATABASE_PREVENTING_COLLECTION + targetCollection + READ_PREFERENCE_DETERMINATION_LOG);
    }

    return readPreference;
  }

  /**
   * Sets current ReadPreference level for read-only related database requests.
   *
   * @param  targetReadPreference  targeted ReadPreference level to assign and use
   */
  public void setReadPreferenceLevel(ReadPreference targetReadPreference)
  {
    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      mongoTemplate.setReadPreference(targetReadPreference);

      String         databaseName = mongoTemplate.getDb().getName();
      ReadPreference preference   = mongoTemplate.getDb().getReadPreference();

      if (LOG.isDebugEnabled())
      {
        LOG.debug(SETTING_READ_PREFERENCE_FOR_DATABASE + databaseName + "] to [" + preference.getName() + ']');
      }
    }
    else
    {
      LOG.error(NULL_MONGODB_DATABASE_PREVENTING_READ_PREFERENCE + targetReadPreference.getName() + ']');
    }
  }

  /**
   * Sets current ReadPreference level for read-only related requests in targetCollectgion.
   *
   * @param  targetCollection      target collection name to assign targetReadPreference
   * @param  targetReadPreference  targeted ReadPreference level to assign and use
   */
  public void setReadPreferenceLevel(String targetCollection, ReadPreference targetReadPreference)
  {
    // sanity check: is mongo service up- it may not be wired up correctly or bound yet
    if (mongoTemplate != null)
    {
      DBCollection collection = mongoTemplate.getDb().getCollection(targetCollection);

      if (collection != null)
      {
        collection.setReadPreference(targetReadPreference);

        String         databaseName = mongoTemplate.getDb().getName();
        ReadPreference preference   = collection.getReadPreference();

        if (LOG.isDebugEnabled())
        {
          LOG.debug(SETTING_READ_PREFERENCE_FOR_DATABASE + databaseName + COLLECTION_LOG + targetCollection + "] to ["
              + preference.getName() + ']');
        }
      }
    }
    else
    {
      LOG.error(NULL_MONGODB_DATABASE_PREVENTING_READ_PREFERENCE + targetReadPreference.getName() + ASSIGNMENT_TO_COLLECTION_LOG
          + targetCollection + ']');
    }
  }

  /**
   * Sanity determination for appropriate ReadPreference level across all read-only related database requests. Inspects the database replicaSet status
   * to decide whether to use ReadPreference.primary() for single databases, or ReadPreference.primaryPreferred() for replicating databases. Defaults
   * to ReadPreference.primary() if no other determination can be made.
   *
   * @return  appropriate ReadPreference level based on the existence of database replicaSet capability/status
   */
  private ReadPreference determineReadPreference()
  {
    ReadPreference readPreference;

    if (isReplicaSet())
    {
      // we are connected to a replicaSet- use ReadPreference.primaryPreferred()
      readPreference = ReadPreference.primaryPreferred();
    }
    else
    {
      // stand-alone database- use ReadPreference.primary()
      readPreference = ReadPreference.primary();
    }

    return readPreference;
  }

}
