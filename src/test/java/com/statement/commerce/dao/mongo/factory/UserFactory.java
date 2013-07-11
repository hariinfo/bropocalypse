package com.statement.commerce.dao.mongo.factory;

import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.user.Role;
import com.statement.commerce.model.user.User;
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
  public static User getNewRetailUser(String merchantId)
  {
    int id = loginSequence++;
    User user = new User();
    user.setFirstName("Irene");
    user.setMiddleName("N");
    user.setLastName("Icabod");
    user.setLocale(Locale.US);
    user.setTimeZone(TimeZone.getTimeZone("US"));
    String email = "irene.icabod@yahoo.com";
    user.setEmail(email);
    user.setLoginName(email + id);
    user.setMerchantId(merchantId);
    user.setRoles(Arrays.asList(Role.RETAIL_USER));
    return user;
  }
}
