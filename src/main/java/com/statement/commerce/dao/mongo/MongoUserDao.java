package com.statement.commerce.dao.mongo;

import com.mongodb.WriteResult;
import com.statement.commerce.dao.UpdateForIdException;
import com.statement.commerce.dao.UserDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.user.Role;
import com.statement.commerce.model.user.User;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.DBOBJECT_ID;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.IGNORE_CASE_MARKER;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.REGEX_BOUNDARY;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Order.ASCENDING;

@Repository("MongoUserDao")
public class MongoUserDao extends MongoDao implements UserDao<User>
{
  public static final String USER_COLLECTION = "UserCollection";
  private static final String FIRST_NAME_FIELD = "firstName";
  private static final String LAST_NAME_FIELD = "lastName";
  private static final String MERCHANT_ID_FIELD = "merchantId";
  private static final String LOGIN_NAME_FIELD = "loginName";
  private static final String EMAIL_FIELD = "email";
  private static final String BILLING_ADDRESS_FIELD = "billingAddress";
  private static final String SHIPPING_ADDRESS_FIELD = "shippingAddress";
  private static final String LOCALE_FIELD = "locale";
  private static final String TIME_ZONE_FIELD = "timeZone";
  private static final String CATALOG_IDS_FIELD = "catalogIds";
  private static final String ROLES_FIELD = "roles";

  @Override
  public void save(User user)
  {
    mongoTemplate.save(user, USER_COLLECTION);
  }

  @Override
  public void delete(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("No ids where provided in the delete call");
    }

    mongoTemplate.remove(new Query(where(DBOBJECT_ID).in((Object[]) ids)), User.class);
  }

  @Override
  public List<User> getByIds(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("Cannot locate users, no ids where provided.");
    }

    Query query = new Query(where(DBOBJECT_ID).in((Object[]) ids));

    return getUserInternal(query);
  }

  @Override
  public List<User> findByName(String name)
  {
    Criteria criteria = new Criteria().orOperator((where(FIRST_NAME_FIELD).regex(REGEX_BOUNDARY + name + REGEX_BOUNDARY, IGNORE_CASE_MARKER)), (where(LAST_NAME_FIELD).regex(REGEX_BOUNDARY + name + REGEX_BOUNDARY, IGNORE_CASE_MARKER)));
    Query query = new Query(criteria);

    return getUserInternal(query);
  }

  @Override
  public void updateBillingAddress(String userId, Address address)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(BILLING_ADDRESS_FIELD, address), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating billing address for user id[" + userId + "], attempted to save billing address: [" + address + "].");
    }
  }

  @Override
  public void updateShippingAddress(String userId, Address address)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(SHIPPING_ADDRESS_FIELD, address), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating shipping address for user id[" + userId + "], attempted to save shipping address: [" + address + "].");
    }
  }

  @Override
  public void updateLocale(String userId, Locale locale)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(LOCALE_FIELD, locale), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating locale for user id[" + userId + "], attempted to save local: [" + locale + "].");
    }
  }

  @Override
  public void updateTimeZone(String userId, String timeZone)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(TIME_ZONE_FIELD, timeZone), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating timezone for user id[" + userId + "], attempted to save timeZone: [" + timeZone + "].");
    }
  }

  @Override
  public void updateCatalogs(String userId, List<String> catalogIds)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(CATALOG_IDS_FIELD, catalogIds), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating catalog ids for user id[" + userId + "], attempted to save catalog ids: [" + catalogIds + "].");
    }
  }

  @Override
  public void updateRoles(String userId, List<Role> roles)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(userId)), Update.update(ROLES_FIELD, roles), User.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating roles for user id[" + userId + "], attempted to save roles: [" + roles + "].");
    }
  }

  private List<User> getUserInternal(Query targetQuery)
  {
    return mongoTemplate.find(targetQuery, User.class);
  }

  /**
   * Initialization.
   */
  @Override
  @PostConstruct
  public void init()
  {
    super.init();

    if (!mongoTemplate.getCollectionNames().contains(USER_COLLECTION))
    {
      mongoTemplate.createCollection(USER_COLLECTION);
    }

    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("id", ASCENDING));
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(FIRST_NAME_FIELD, ASCENDING));
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(LAST_NAME_FIELD, ASCENDING));
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(EMAIL_FIELD, ASCENDING));
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(LOGIN_NAME_FIELD, ASCENDING));
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(LOGIN_NAME_FIELD, ASCENDING).on(MERCHANT_ID_FIELD, ASCENDING).unique());
    mongoTemplate.indexOps(User.class).ensureIndex(new Index().on(EMAIL_FIELD, ASCENDING).on(MERCHANT_ID_FIELD, ASCENDING).unique());
  }
}
