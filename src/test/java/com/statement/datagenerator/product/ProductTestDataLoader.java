package com.statement.datagenerator.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.elasticsearch.TestProductDataConfig;
import com.statement.commerce.model.product.Product;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
  private JestClient client;

  @Test(groups = "DataLoader")
  public void loadProduct() throws Exception
  {
    URL url = ProductTestDataLoader.class.getResource(testProductDataConfig.getDataFile());
    URI uri = url.toURI();
    List<String> productLines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();
    Bulk.Builder builder = new Bulk.Builder()
        .defaultIndex(testProductDataConfig.getIndexName())
        .defaultType(testProductDataConfig.getIndexType());

    for(String product : productLines)
    {
      Product product1 = mapper.readValue(product, Product.class);
      builder.addAction(new Index.Builder(product1).build());
    }

    client.execute(builder.build());
  }

  @Test(groups = "DropData")
  public void clearDataByType() throws Exception
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
