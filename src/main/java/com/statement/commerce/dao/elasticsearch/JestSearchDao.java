package com.statement.commerce.dao.elasticsearch;

import com.google.gson.JsonArray;
import com.statement.commerce.dao.DataException;
import com.statement.commerce.dao.SearchDao;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.MultiGet;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/17/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JestSearchDao implements SearchDao<SearchCriteria>
{
  private static final Logger LOG = LoggerFactory.getLogger(JestSearchDao.class);

  @Autowired
  private ProductDataConfig productDataConfig;
  private JestClient client;

  @Override
  public SearchResults<Product> search(SearchCriteria searchCriteria)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SearchResults<Product> termSearch(SearchCriteria searchCriteria)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public Product[] addProducts(Product... products) throws DataException
  {
    Bulk.Builder builder = new Bulk.Builder()
        .defaultIndex(productDataConfig.getIndexName())
        .defaultType(productDataConfig.getIndexType());

    for(Product product : products)
    {
      builder.addAction(new Index.Builder(product).build());
    }

    Product[] results;

    try
    {
      JestResult jestResult = client.execute(builder.build());
      checkForResultError(jestResult);
      results = processResultSet(jestResult, products);
    }
    catch(Exception e)
    {
      throw new DataException("Error adding provided product to the search index", e);
    }

    return results;
  }

  public Product[] getByIds(String... ids) throws DataException
  {
    MultiGet.Builder.ById builder = new MultiGet.Builder.ById(productDataConfig.getIndexName(), productDataConfig.getIndexType());
    builder.addId(Arrays.asList(ids));
    List<Product> products;

    try
    {
      JestResult result = client.execute(builder.build());
      checkForResultError(result);
      products = result.getSourceAsObjectList(Product.class);
    }
    catch(Exception e)
    {
      throw new DataException("Error executing getById query.", e);
    }

    return products.toArray(new Product[products.size()]);
  }

  private void checkForResultError(JestResult result) throws DataException
  {
    if(!result.isSucceeded())
    {
      throw new DataException(result.getErrorMessage());
    }
  }

  public Product[] processResultSet(JestResult result, Product... products)
  {
    JsonArray itemsArray = (JsonArray)result.getJsonObject().get("items");

    for(int i = 0; i < products.length; ++i)
    {
      String id = itemsArray.get(i).getAsJsonObject().get(JestConstants.CREATE_MAP).getAsJsonObject().get(JestConstants.ID_FIELD).getAsString();
      products[i].setId(id);
    }
    return products;
  }

  @PostConstruct
  public void init() throws Exception
  {
    LOG.error("Starting up JestSearchDao.");
    // Configuration
    ClientConfig clientConfig = new ClientConfig.Builder(productDataConfig.getServerUrl()).multiThreaded(true).build();

    // Construct a new Jest client according to configuration via factory
    JestClientFactory factory = new JestClientFactory();
    factory.setClientConfig(clientConfig);
    client = factory.getObject();

    // check to see if our index exists, if not, create it.
    IndicesExists indicesExists = new IndicesExists.Builder().addIndex(productDataConfig.getIndexName()).build();
    JestResult result = client.execute(indicesExists);

    if(!result.isSucceeded())
    {
      // create the index!
      CreateIndex newIndex = new CreateIndex.Builder(productDataConfig.getIndexName()).build();
      JestResult createResult = client.execute(newIndex);

      if(!createResult.isSucceeded())
      {
        throw new Exception("Error creating ElasticSearch index [" + productDataConfig.getIndexName() + "].");
      }
    }
  }

  @PreDestroy
  public void cleanup()
  {
    if(null != client)
    {
      client.shutdownClient();
    }
  }
}
