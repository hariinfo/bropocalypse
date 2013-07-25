package com.statement.commerce.dao;

import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.user.Role;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public interface UserDao<T> extends GenericDao<T>
{
  /**
   * Update the user's billing address with the provided data
   * @param userId the user id
   * @param address the address data; fully populated
   */
  void updateBillingAddress(String userId, Address address);

  /**
   * Update the users' shipping address with the provided data
   * @param userId the user id
   * @param address the address data; fully populated
   */
  void updateShippingAddress(String userId, Address address);

  /**
   * Update the user's locale
   * @param userId the user id
   * @param locale the locale
   */
  void updateLocale(String userId, String locale);

  /**
   * Update the user's timezone
   * @param userId the user id
   * @param timeZone the timezone
   */
  void updateTimeZone(String userId, String timeZone);

  /**
   * Update the user's catalogs
   * @param userId the user id
   * @param catalogIds the catalog ids
   */
  void updateCatalogs(String userId, List<String> catalogIds);

  /**
   * Update the user's roles
   * @param userId the user id
   * @param roles the roles
   */
  void updateRoles(String userId, List<Role> roles);
}
