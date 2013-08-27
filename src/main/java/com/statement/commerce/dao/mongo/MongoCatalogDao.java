package com.statement.commerce.dao.mongo;

import com.mongodb.WriteResult;
import com.statement.commerce.dao.CatalogDao;
import com.statement.commerce.dao.UpdateForIdException;
import com.statement.commerce.model.core.MultilingualString;
import com.statement.commerce.model.product.Catalog;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.DBOBJECT_ID;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.IGNORE_CASE_MARKER;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.REGEX_BOUNDARY;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Order.ASCENDING;
import static org.springframework.data.mongodb.core.query.Update.update;


@Repository("MongoCatalogDao")
public class MongoCatalogDao extends MongoDao implements CatalogDao<Catalog>
{
  public static final String CATALOG_COLLECTION = "CatalogCollection";
  public static final String EXTERNAL_ID_FIELD = "externalId";
  public static final String CUSTOMER_ID_FIELD = "customerId";
  public static final String CATALOG_NAME_FIELD = "catalogName";
  private static final Logger LOG = LoggerFactory.getLogger(MongoCatalogDao.class);

  @Override
  public List<Catalog> getCatalogByExternalId(String... externalIds)
  {
    Query query = new Query(where(EXTERNAL_ID_FIELD).in(externalIds));
    return getCatalogsInternal(query);
  }

  @Override
  public void updateCatalogName(String catalogId, MultilingualString catalogName)
  {
    WriteResult result = mongoTemplate.updateFirst(new Query(where(DBOBJECT_ID).is(catalogId)), update(CATALOG_NAME_FIELD, catalogName), Catalog.class);

    if (result.getN() < 1)
    {
      throw new UpdateForIdException("Error updating the catalog name. ");
    }
  }

  @Override
  public List<Catalog> getCatalogsByCustomerId(String customerId)
  {
    Query query = new Query(where(CUSTOMER_ID_FIELD).is(customerId));
    return getCatalogsInternal(query);
  }

  @Override
  public List<String> getCatalogProducts(String catalogId)
  {
    List<Catalog> catalogs = getByIds(catalogId);
    if(catalogs.isEmpty())
    {
      return null;
    }

    return catalogs.get(0).getProductIds();
  }

  @Override
  public void save(Catalog catalog)
  {
    if(null == catalog)
    {
      throw new IllegalArgumentException("The provided catalog was null");
    }

    mongoTemplate.save(catalog, CATALOG_COLLECTION);
  }

  @Override
  public void delete(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("No ids were provided in the delete call");
    }

    mongoTemplate.remove(new Query(where(DBOBJECT_ID).in((Object[]) ids)),  Catalog.class);
  }

  @Override
  public List<Catalog> getByIds(String... ids)
  {
    if ((null == ids) || (ids.length < 1))
    {
      throw new IllegalArgumentException("Cannot locate catalogs, no ids were provided.");
    }

    Query query = new Query(where(DBOBJECT_ID).in((Object[]) ids));

    return getCatalogsInternal(query);
  }

  @Override
  public List<Catalog> findByName(String name)
  {
    Query query = new Query(where(CATALOG_NAME_FIELD + ".translatedStrings").regex(REGEX_BOUNDARY + name + REGEX_BOUNDARY,IGNORE_CASE_MARKER));
    return getCatalogsInternal(query);
  }

  private List<Catalog> getCatalogsInternal(Query targetQuery)
  {
    return mongoTemplate.find(targetQuery, Catalog.class);
  }

  /** Initialization. */
  @Override
  @PostConstruct
  public void init()
  {
    super.init();

    if (!mongoTemplate.getCollectionNames().contains(CATALOG_COLLECTION))
    {
      mongoTemplate.createCollection(CATALOG_COLLECTION);
    }

    mongoTemplate.indexOps(Catalog.class).ensureIndex(new Index().on("id", ASCENDING));
    mongoTemplate.indexOps(Catalog.class).ensureIndex(new Index().on(CATALOG_NAME_FIELD, ASCENDING));
    mongoTemplate.indexOps(Catalog.class).ensureIndex(new Index().on(CUSTOMER_ID_FIELD, ASCENDING));
    mongoTemplate.indexOps(Catalog.class).ensureIndex(new Index().on(EXTERNAL_ID_FIELD, ASCENDING));
  }
}
