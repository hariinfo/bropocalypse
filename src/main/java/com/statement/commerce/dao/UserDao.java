package com.statement.commerce.dao;

import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.user.Role;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public interface UserDao<T> extends GenericDao<T>
{
  void updateBillingAddress(String userId, Address address);
  void updateShippingAddress(String userId, Address address);
  void updateLocale(String userId, Locale locale);
  void updateTimeZone(String userId, String timeZone);
  void updateCatalogs(String userId, List<String> catalogIds);
  void updateRoles(String userId, List<Role> roles);
}
