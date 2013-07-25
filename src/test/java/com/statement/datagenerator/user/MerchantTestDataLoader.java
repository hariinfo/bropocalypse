package com.statement.datagenerator.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.MongoMerchantDao;
import com.statement.commerce.dao.mongo.TestMerchantDataConfig;
import com.statement.commerce.model.core.Merchant;
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

/**
 * Merchant test data loaded
 * User: dedrick
 * Date: 7/24/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class MerchantTestDataLoader  extends AbstractTestNGSpringContextTests
{
  @Autowired
  private TestMerchantDataConfig testMerchantDataConfig;
  private String MERCHANT_COLLECTION = MongoMerchantDao.MERCHANT_COLLECTION;

  @Test(groups = "DataLoader")
  public void loadSampleMerchantData() throws Exception
  {
    URL url = UserTestDataLoader.class.getResource(testMerchantDataConfig.getDataFile());
    URI uri = url.toURI();
    List<String> merchantLines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();

    for(String merchantJson : merchantLines)
    {
      Merchant merchant = mapper.readValue(merchantJson, Merchant.class);

      testMerchantDataConfig.getMongoTemplate().save(merchant, MERCHANT_COLLECTION);
    }
  }

  @Test(groups = "DropData")
  public void dropMerchantData() throws Exception
  {
    testMerchantDataConfig.getMongoTemplate().dropCollection(MERCHANT_COLLECTION);
  }

  @BeforeClass(groups = {"DataLoader"})
  public void setup() throws Exception
  {
    if (!testMerchantDataConfig.getMongoTemplate().getCollectionNames().contains(MERCHANT_COLLECTION))
    {
      testMerchantDataConfig.getMongoTemplate().createCollection(MERCHANT_COLLECTION);
    }
  }

}
