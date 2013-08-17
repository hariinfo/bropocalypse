package com.statement.datagenerator.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.MongoMerchantDao;
import com.statement.commerce.dao.mongo.MongoUserDao;
import com.statement.commerce.dao.mongo.TestMerchantDataConfig;
import com.statement.commerce.dao.mongo.TestUserDataConfig;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.user.User;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.IGNORE_CASE_MARKER;
import static com.statement.commerce.dao.mongo.MongoDaoConstants.REGEX_BOUNDARY;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * User test data loader
 * User: dedrick
 * Date: 7/24/13
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class UserTestDataLoader extends AbstractTestNGSpringContextTests
{
  private static final String USER_COLLECTION = MongoUserDao.USER_COLLECTION;
  private static final String MERCHANT_COLLECTION = MongoMerchantDao.MERCHANT_COLLECTION;
  @Autowired
  private TestUserDataConfig testUserDataConfig;
  @Autowired
  private TestMerchantDataConfig testMerchantDataConfig;
  private Map<String, Merchant> merchantMap = new HashMap<>();

  @BeforeClass(groups = {"DataLoader"})
  public void setup() throws Exception
  {
    if (!testUserDataConfig.getMongoTemplate().getCollectionNames().contains(USER_COLLECTION))
    {
      testUserDataConfig.getMongoTemplate().createCollection(USER_COLLECTION);
    }
    if (!testMerchantDataConfig.getMongoTemplate().getCollectionNames().contains(MERCHANT_COLLECTION))
    {
      testMerchantDataConfig.getMongoTemplate().createCollection(MERCHANT_COLLECTION);
    }
  }

  @Test(groups = "DataLoader", dependsOnMethods = "loadSampleMerchantData")
  public void loadTestUserData() throws Exception
  {
    URL url = UserTestDataLoader.class.getResource(testUserDataConfig.getDataFile());
    URI uri = url.toURI();
    List<String> userLines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

    ObjectMapper mapper = new ObjectMapper();

    for(String userJson : userLines)
    {
      User user = mapper.readValue(userJson, User.class);
      Merchant merchant = merchantMap.get(user.getMerchantName());

      if(null == merchant)
      {
        merchant = getTestMerchant(user.getMerchantName());
      }

      user.setMerchantId(merchant.getId());

      // now save the user
      testUserDataConfig.getMongoTemplate().save(user, USER_COLLECTION);
    }
  }

  private Merchant getTestMerchant(String merchantName) throws Exception
  {
    Query query = new Query(where("name").regex(REGEX_BOUNDARY + merchantName + REGEX_BOUNDARY,IGNORE_CASE_MARKER));

    List<Merchant> merchants =
        testUserDataConfig.getMongoTemplate().find(query, Merchant.class, MongoMerchantDao.MERCHANT_COLLECTION);

    Assert.assertFalse(merchants.isEmpty());

    Merchant merchant = merchants.get(0);
    merchantMap.put(merchant.getName(), merchant);
    return merchant;
  }

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

  // ------------------------------------------------------------------------------------------------------------------
  // drop data code below

  @Test(groups = "DropData")
  public void dropUserData() throws Exception
  {
    testUserDataConfig.getMongoTemplate().dropCollection(USER_COLLECTION);
  }
  @Test(groups = "DropData", dependsOnMethods = "dropUserData")
  public void dropMerchantData() throws Exception
  {
    testMerchantDataConfig.getMongoTemplate().dropCollection(MERCHANT_COLLECTION);
  }
}
