package com.statement.commerce.dao.mongo;

import com.mongodb.WriteResult;
import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.dao.MerchantDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.core.ObjectForIdNotFoundException;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;

import static com.statement.commerce.dao.mongo.MongoDaoConstants.DBOBJECT_ID;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.IGNORE_CASE_MARKER;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.REGEX_BOUNDARY;
import static org.springframework.data.mongodb.core.query.Order.ASCENDING;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository("MongoMerchantDao")
public class MongoMerchantDao extends MongoDao implements MerchantDao<Merchant>
{
  public static final String MERCHANT_COLLECTION = "MerchantCollection";
  private static final String NAME_FIELD = "name";
  private static final String ADDRESS_FIELD = "billingAddress";

  @Override
  public void updateAddress(String id, Address address)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(id)), update(ADDRESS_FIELD, address), Merchant.class);

    if (result.getN() < 1)
    {
      throw new ObjectForIdNotFoundException("Error updating the Merchant with new address");
    }
  }

  @Override
  public void save(Merchant merchant)
  {
    if(null == merchant)
    {
      throw new IllegalArgumentException("The provided merchant was null");
    }

    mongoTemplate.save(merchant, MERCHANT_COLLECTION);
  }

  @Override
  public void delete(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("No ids where provided in the delete call");
    }

    mongoTemplate.remove(new Query(where(DBOBJECT_ID).in((Object[]) ids)),  Merchant.class);
  }

  @Override
  public List<Merchant> getByIds(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("Cannot locate merchants, no ids where provided.");
    }

    Query query = new Query(where(DBOBJECT_ID).in((Object[]) ids));

    return getMerchantsInternal(query);
  }

  @Override
  public boolean merchantExists(String merchantId)
  {
    Query query = new Query(where(DBOBJECT_ID).is(merchantId));
    long count = mongoTemplate.count(query, Merchant.class);
    return count > 0;
  }

  @Override
  public List<Merchant> findByName(String name)
  {
    Query query = new Query(where(NAME_FIELD).regex(REGEX_BOUNDARY + name + REGEX_BOUNDARY,IGNORE_CASE_MARKER));
    return getMerchantsInternal(query);
  }

  private List<Merchant> getMerchantsInternal(Query targetQuery)
  {
    return mongoTemplate.find(targetQuery, Merchant.class);
  }

  /** Initialization. */
  @Override
  @PostConstruct
  public void init()
  {
    super.init();

    if (!mongoTemplate.getCollectionNames().contains(MERCHANT_COLLECTION))
    {
      mongoTemplate.createCollection(MERCHANT_COLLECTION);
    }

    mongoTemplate.indexOps(Merchant.class).ensureIndex(new Index().on("id", ASCENDING));
    mongoTemplate.indexOps(Merchant.class).ensureIndex(new Index().on(NAME_FIELD, ASCENDING));
  }
}
