package com.statement.commerce.dao.mongo;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.model.user.User;
import com.statement.commerce.model.user.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class MongoUserDaoIntTest extends AbstractTestNGSpringContextTests
{
  private List<User> deleteList = new ArrayList<>();
  @Autowired
  private MongoUserDao mongoUserDao;

  @AfterTest(groups = "int")
  public void testCleanup()
  {
    List<String> ids = new ArrayList<>();
    for(User user : deleteList)
    {
      ids.add(user.getId());
    }

    mongoUserDao.delete(ids.toArray(new String[ids.size()]));
    ids.clear();
  }

  @Test(groups = "int")
  public void testSave()
  {
    User user = new User();
    user.setFirstName("Irene");
    user.setMiddleName("N");
    user.setLastName("Icabod");
    user.setLocale(Locale.US);
    user.setTimeZone(TimeZone.getTimeZone("US"));
    user.setLoginName("irene.icabod@yahoo.com");
    user.setMerchantId("1");
    user.setRoles(Arrays.asList(Role.RETAIL_USER));
    deleteList.add(user);
    mongoUserDao.save(user);

    Assert.assertNotNull(user.getId());
  }

  @Test(groups = "int")
  public void testGetById()
  {
    User user = new User();
    user.setFirstName("Irene");
    user.setMiddleName("N");
    user.setLastName("Icabod");
    user.setLocale(Locale.US);
    user.setTimeZone(TimeZone.getTimeZone("US"));
    user.setLoginName("irene.icabod@yahoo.com");
    user.setMerchantId("1");
    user.setRoles(Arrays.asList(Role.RETAIL_USER));
    deleteList.add(user);
    mongoUserDao.save(user);

    User retrievedUser = mongoUserDao.getByIds(user.getId()).get(0);
    Assert.assertNotNull(retrievedUser, "The user should not be null");
    Assert.assertEquals(retrievedUser, user, "The users are not the same!");
  }
}
