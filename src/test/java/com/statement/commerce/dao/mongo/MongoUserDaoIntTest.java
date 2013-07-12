package com.statement.commerce.dao.mongo;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.factory.UserFactory;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.user.User;
import com.statement.commerce.model.user.Role;
import com.statement.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

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

    assertNotNull(user.getId());
  }

  @Test(groups = "int")
  public void testGetById()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    User retrievedUser = mongoUserDao.getByIds(user.getId()).get(0);
    assertNotNull(retrievedUser, "The user should not be null");
    assertEquals(retrievedUser, user, "The users are not the same!");
  }

  @Test(groups = "int")
  public void testGetByName()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    List<User> users = mongoUserDao.findByName(user.getFirstName());

    assertNotNull(users, "The user list should not be null");
    assertFalse(users.isEmpty(), "The user list should not be empty");
    assertEquals(users.size(), 1, "There should be exactly 1 match");
    assertEquals(users.get(0), user, "The users should match");

    users = mongoUserDao.findByName(user.getFirstName().toUpperCase());
    assertNotNull(users, "The user list should not be null");
    assertFalse(users.isEmpty(), "The user list should not be empty");
    assertEquals(users.size(), 1, "There should be exactly 1 match");
    assertEquals(users.get(0), user, "The users should match");
  }

  @Test(groups = "int")
  public void testUpdateBillingAddress()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    Address originalAddress = user.getBillingAddress();
    Address newAddress = new Address();

    Util.copyPojo(originalAddress, newAddress);

    newAddress.setAddress1(originalAddress.getAddress1() + " Suite 1101");
    newAddress.setCity("Beaverton");

    mongoUserDao.updateBillingAddress(user.getId(), newAddress);

    User userWithNewAddress = mongoUserDao.getByIds(user.getId()).get(0);
    assertEquals(userWithNewAddress.getBillingAddress(), newAddress, "The address objects should be the same");
  }

  @Test(groups = "int")
  public void testUpdateShippingAddress()
  {
    User user = UserFactory.getNewRetailUser("1");
    deleteList.add(user);
    mongoUserDao.save(user);

    Address originalAddress = user.getShippingAddress();
    Address newAddress = new Address();

    Util.copyPojo(originalAddress, newAddress);

    newAddress.setAddress1(originalAddress.getAddress1() + " Suite 1101");
    newAddress.setCity("Beaverton");

    mongoUserDao.updateShippingAddress(user.getId(), newAddress);

    User userWithNewAddress = mongoUserDao.getByIds(user.getId()).get(0);
    assertEquals(userWithNewAddress.getShippingAddress(), newAddress, "The address objects should be the same");
  }

  @Test(groups = "int")
  public void testUpdateLocale()
  {
    User user = UserFactory.getNewRetailUser("8");
    deleteList.add(user);
    mongoUserDao.save(user);

    Locale locale = Locale.UK;
    assertNotEquals(user.getLocale(), locale, "The locales should not match. If they do, check the UserFactory");
    mongoUserDao.updateLocale(user.getId(), locale);

    User updatedUser = mongoUserDao.getByIds(user.getId()).get(0);
    assertEquals(updatedUser.getLocale(), locale, "The locale objects should match");
  }

  @Test(groups = "int")
  public void testUpdateTimeZone()
  {
    User user = UserFactory.getNewRetailUser("8");
    deleteList.add(user);
    mongoUserDao.save(user);

    TimeZone timeZone = TimeZone.getTimeZone("PST");


    assertNotEquals(user.getTimeZone(), timeZone, "The timeZones should not match. If they do, check the UserFactory");
    mongoUserDao.updateTimeZone(user.getId(), "PST");

    User updatedUser = mongoUserDao.getByIds(user.getId()).get(0);
    assertEquals(updatedUser.getTimeZone(), timeZone.getID(), "The updated timeZone should have been saved, but it wasn't.");
  }

  @Test(groups = "int")
  public void testUpdateRoles()
  {
    User user = UserFactory.getNewAdmin("8");
    deleteList.add(user);
    mongoUserDao.save(user);

    Role deletedRole = user.getRoles().get(0);
    user.getRoles().remove(0);

    int newRoleCount = user.getRoles().size();
    mongoUserDao.updateRoles(user.getId(), user.getRoles());

    User updatedUser = mongoUserDao.getByIds(user.getId()).get(0);
    assertEquals(updatedUser.getRoles().size(), newRoleCount, "The sizes of the role lists should match.");

    for(Role role : user.getRoles())
    {
      if(role.equals(deletedRole))
      {
        fail("Found role that should have been deleted!");
      }
    }
  }
}
