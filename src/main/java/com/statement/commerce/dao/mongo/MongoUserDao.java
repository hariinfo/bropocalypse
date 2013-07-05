package com.statement.commerce.dao.mongo;

import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.user.User;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.DBOBJECT_ID;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Order.ASCENDING;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/3/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MongoUserDao extends MongoDao implements GenericDao<User>
{
  public static final String USER_COLLECTION = "UserCollection";

  @Override
  public void save(User abstractUser)
  {
    mongoTemplate.save(abstractUser, USER_COLLECTION);
  }

  @Override
  public void delete(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("No ids where provided in the delete call");
    }

    mongoTemplate.remove(new Query(where(DBOBJECT_ID).in(ids)),  User.class);
  }

  @Override
  public List<User> getByIds(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("Cannot locate users, no ids where provided.");
    }

    Query query = new Query(where(DBOBJECT_ID).in((Object[]) ids));

    return getUserInternal(query);  }

  @Override
  public List<User> findByName(String name)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  private List<User> getUserInternal(Query targetQuery)
  {
    return mongoTemplate.find(targetQuery, User.class);
  }

  /** Initialization. */
  @Override
  @PostConstruct
  public void init()
  {
    super.init();

    if (!mongoTemplate.getCollectionNames().contains(USER_COLLECTION))
    {
      mongoTemplate.createCollection(USER_COLLECTION);
    }

    mongoTemplate.indexOps(Merchant.class).ensureIndex(new Index().on("id", ASCENDING));
//    mongoTemplate.indexOps(Merchant.class).ensureIndex(new Index().on(NAME_FIELD, ASCENDING));
  }
}
