package com.statement.commerce.dao.mongo.factory;

import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Country;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.core.Provence;
import com.statement.commerce.model.user.Role;
import com.statement.commerce.model.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/3/13
 * Time: 10:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserFactory
{
  private static int loginSequence = 0;
  private static int addressSequence = 100;


  public static User getNewAdmin(String merchantId)
  {
    int id = loginSequence++;
    User user = new User();
    user.setFirstName("Flava");
    user.setLastName("Flave");
    user.setLocale(Locale.US);
    user.setTimeZone("PST");
    String email = "flava.flave@gmail.com";
    user.setEmail(email);
    user.setLoginName(email + id);
    user.setMerchantId(merchantId);
    ArrayList<Role> roles = new ArrayList<>();
    roles.addAll(Arrays.asList(Role.MERCHANT_ADMIN, Role.SYSTEM_ADMIN, Role.MERCHANT_CATALOG_ADMIN));
    user.setRoles(roles);

    return user;
  }

  public static User getNewRetailUser(String merchantId)
  {
    int id = loginSequence++;
    User user = new User();
    user.setFirstName("Irene");
    user.setMiddleName("N");
    user.setLastName("Icabod");
    user.setLocale(Locale.US);
    user.setTimeZone("EST");
    String email = "irene.icabod@yahoo.com";
    user.setEmail(email);
    user.setLoginName(email + id);
    user.setMerchantId(merchantId);
    user.setRoles(Arrays.asList(Role.RETAIL_USER));

    user.setBillingAddress(generateAddress());
    user.setShippingAddress(generateAddress());

    return user;
  }

  private static Address generateAddress()
  {
    int seq = addressSequence++;
    Address address = new Address();
    address.setAddress1("100 Random Street");
    address.setCity("Portland");
    address.setZipCode("97006");
    address.setProvence(Provence.OREGON);
    address.setCountry(Country.US);
    address.setName("Random address name " + seq);

    return address;
  }
}
