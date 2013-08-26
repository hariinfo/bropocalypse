package com.statement.datagenerator.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.elasticsearch.JestConstants;
import com.statement.commerce.dao.elasticsearch.JestSearchDao;
import com.statement.commerce.dao.elasticsearch.TestProductDataConfig;
import com.statement.commerce.model.core.MultilingualString;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.product.ProductType;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

/**
 * Product data loader
 * User: dedrick
 * Date: 7/22/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class ProductTestDataLoader extends AbstractTestNGSpringContextTests
{
  private static final Logger LOG = Logger.getLogger(ProductTestDataLoader.class);
  @Autowired
  private TestProductDataConfig testProductDataConfig;
  @Autowired
  private JestSearchDao dao;
  private JestClient client;

  @Test(groups = "DataLoader")
  public void loadProduct() throws Exception
  {
    URL url = ProductTestDataLoader.class.getResource(testProductDataConfig.getDataFile());
    URI uri = url.toURI();
    // read the data file; each line in the file is one valid JSon value
    List<String> productLines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    Bulk.Builder builder = new Bulk.Builder()
        .defaultIndex(testProductDataConfig.getIndexName())
        .defaultType(testProductDataConfig.getIndexType());

    List<Product> productList = new ArrayList<>();
    for(String product : productLines)
    {
      Product product1 = mapper.readValue(product, Product.class);
      builder.addAction(new Index.Builder(product1).build());
      productList.add(product1);
    }

    JestResult result = client.execute(builder.build());

    Assert.assertTrue(result.isSucceeded(), "The operation did not succeed");
    JsonArray itemsArray = (JsonArray)result.getJsonObject().get("items");
    Assert.assertEquals(itemsArray.size(), productList.size(), "The resulting list is not the same as the input list of products");

    Product[] productArray = dao.processResultSet(result, productList.toArray(new Product[productList.size()]));
    for(Product p : productArray)
    {
      Assert.assertTrue(StringUtils.isNotEmpty(p.getId()));
    }
  }

  @Test(groups = "DataLoader", enabled = false)
  public void testIndexProduct() throws Exception
  {
    Product product = new Product();
    MultilingualString productName = new MultilingualString("en_US", "Irene1");
    product.setProductName(productName);
    product.setProductType(ProductType.PRODUCT);
    product.setRetailPrice(99.99);
//    product.setId("dedrick1");

    Index.Builder builder = new Index.Builder(product).index(testProductDataConfig.getIndexName()).type(testProductDataConfig.getIndexType());
    JestResult result = client.execute(builder.build());
    Assert.assertTrue(result.isSucceeded());
    String id = result.getJsonObject().get(JestConstants.ID_FIELD).getAsString();
    product.setId(id);
  }

  @Test(groups = "DataLoader", enabled = false)
  public void testGetById() throws Exception
  {
    Get.Builder builder = new Get.Builder("rBFzxrvyTOu94BqqywZayw").index(testProductDataConfig.getIndexName()).type(testProductDataConfig.getIndexType());

    JestResult result = client.execute(builder.build());

    Assert.assertNotNull(result);
    Product product = result.getSourceAsObject(Product.class);
    Assert.assertTrue(StringUtils.isNotEmpty(product.getId()));
  }

  @Test(groups = "DataLoader", enabled = true)
  public void testSearch() throws Exception
  {
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//    searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
//    searchSourceBuilder.query(QueryBuilders.wildcardQuery("productName", "FOUR INCH BAGGY SHORT"));
    QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryString("TEAM SPOR*");
    queryStringQueryBuilder.defaultField("productName");
    searchSourceBuilder.query(queryStringQueryBuilder.field("productName.translatedStrings.*").field("upc")); // .defaultField("productName")
//    searchSourceBuilder.facet(new FacetBuilder());
    searchSourceBuilder.from(0);
    searchSourceBuilder.size(100);
//    searchSourceBuilder.

    LOG.error(searchSourceBuilder.toString());
//    searchSourceBuilder.query(QueryBuilders.wildcardQuery("upc","008*"));
//    searchSourceBuilder.query(QueryBuilders.termQuery("retailPrice",35.0));
//    searchSourceBuilder.query(QueryBuilders.wildcardQuery("retailPrice","3"));


    // Need to figure this out
    Search.Builder builder = new Search.Builder(searchSourceBuilder.toString()).
        addIndex(testProductDataConfig.getIndexName()).
        addType(testProductDataConfig.getIndexType());

    JestResult result = client.execute(builder.build());
    LOG.error(result.getSourceAsObjectList(Product.class).size());

    if(!result.isSucceeded())
    {
      LOG.error(result.getErrorMessage());
    }
    else
    {
      List<Product> productList = result.getSourceAsObjectList(Product.class);
      Assert.assertFalse(productList.isEmpty(), "We should have received search results!");
      int max = 10;
      for(int i = 0; i < max && i < productList.size(); ++i)
      {
        LOG.error(productList.get(i));
      }
    }


    Assert.assertTrue(result.isSucceeded());
  }

  @Test(groups = "DropData")
  public void clearProductData() throws Exception
  {
    DeleteIndex deleteIndex = new DeleteIndex.Builder(testProductDataConfig.getIndexName()).build();
    JestResult result = client.execute(deleteIndex);

    if(!result.isSucceeded())
    {
      throw new Exception("Error clearing index!");
    }
  }

  @BeforeClass(groups = {"DataLoader","DropData"})
  public void setup() throws Exception
  {
    // Configuration
    ClientConfig clientConfig = new ClientConfig.Builder(testProductDataConfig.getServerUrl()).multiThreaded(true).build();

    // Construct a new Jest client according to configuration via factory
    io.searchbox.client.JestClientFactory factory = new io.searchbox.client.JestClientFactory();
    factory.setClientConfig(clientConfig);
    client = factory.getObject();

    // check to see if our index exists, if not, create it.
    IndicesExists indicesExists = new IndicesExists.Builder().addIndex(testProductDataConfig.getIndexName()).build();
    JestResult result = client.execute(indicesExists);

    if(!result.isSucceeded())
    {
      // create the index!
      CreateIndex newIndex = new CreateIndex.Builder(testProductDataConfig.getIndexName()).build();
      JestResult createResult = client.execute(newIndex);

      if(!createResult.isSucceeded())
      {
        throw new Exception("Error creating ElasticSearch index [" + testProductDataConfig.getIndexName() + "].");
      }
    }
  }
}
