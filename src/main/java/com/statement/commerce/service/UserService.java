package com.statement.commerce.service;

import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.dao.MerchantDao;
import com.statement.commerce.dao.UserDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.ObjectForIdNotFoundException;
import com.statement.commerce.model.user.Role;
import com.statement.commerce.model.user.User;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/10/13
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserService
{
  private static final Log LOG = LogFactory.getLog(UserService.class);
  @Autowired
  @Qualifier("MongoUserDao")
  private UserDao userDao;
  @Autowired
  @Qualifier("MongoMerchantDao")
  private MerchantDao merchantDao;

  public void delete(String... ids)
  {
    userDao.delete(ids);
  }

  public List<User> getByIds(String... ids)
  {
    return userDao.getByIds(ids);
  }

  public void save(User user)
  {
    if(StringUtils.isEmpty(user.getMerchantId()))
    {
      throw new IllegalArgumentException("User must have a merchant");
    }

    // TODO add code to ensure that user's merchant ID has not been changed
    userDao.save(user);
  }

  public void createNewUser(User user)
  {
    if(StringUtils.isEmpty(user.getMerchantId()))
    {
      throw new IllegalArgumentException("User must have a merchant");
    }

    if(!merchantDao.merchantExists(user.getMerchantId()))
    {
      throw new ObjectForIdNotFoundException("Merchant identified by id [" + user.getMerchantId() + "] does not exist.");
    }

    if(StringUtils.isNotEmpty(user.getId()))
    {
      throw new IllegalArgumentException("This is a create user operation. This method cannot be used to update an existing user.");
    }

    userDao.save(user);
  }

  public void updateTimezone(String userId, String timeZone)
  {
    userDao.updateTimeZone(userId, timeZone);
  }

  public void updateShippingAddress(String userId, Address address)
  {
    userDao.updateShippingAddress(userId, address);
  }

  public void updateBillingAddress(String userId, Address address)
  {
    userDao.updateBillingAddress(userId, address);
  }

  public void updateCatalogIds(String userId, List<String> catalogIds)
  {
    userDao.updateCatalogs(userId, catalogIds);
  }

  public void updateRoles(String userId, List<Role> roles)
  {
    userDao.updateRoles(userId, roles);
  }

  public void updateLocale(String userId, String locale)
  {
    userDao.updateLocale(userId, locale);
  }
}
