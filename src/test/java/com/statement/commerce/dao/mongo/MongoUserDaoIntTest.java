package com.statement.commerce.dao.mongo;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.factory.UserFactory;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class MongoUserDaoIntTest extends AbstractTestNGSpringContextTests
{
  private List<User> deleteList = new ArrayList<>();
  @Autowired
  private MongoUserDao mongoUserDao;

  @AfterMethod(groups = "int")
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
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    Assert.assertNotNull(user.getId());
  }

  @Test(groups = "int")
  public void testGetById()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    User retrievedUser = mongoUserDao.getByIds(user.getId()).get(0);
    Assert.assertNotNull(retrievedUser, "The user should not be null");
    Assert.assertEquals(retrievedUser, user, "The users are not the same!");
  }

  @Test(groups = "int")
  public void testGetByName()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    List<User> users = mongoUserDao.findByName(user.getFirstName());

    Assert.assertNotNull(users, "The user list should not be null");
    Assert.assertFalse(users.isEmpty(), "The user list should not be empty");
    Assert.assertEquals(users.size(), 1, "There should be exactly 1 match");
    Assert.assertEquals(users.get(0), user, "The users should match");

    users = mongoUserDao.findByName(user.getFirstName().toUpperCase());
    Assert.assertNotNull(users, "The user list should not be null");
    Assert.assertFalse(users.isEmpty(), "The user list should not be empty");
    Assert.assertEquals(users.size(), 1, "There should be exactly 1 match");
    Assert.assertEquals(users.get(0), user, "The users should match");
  }
}
