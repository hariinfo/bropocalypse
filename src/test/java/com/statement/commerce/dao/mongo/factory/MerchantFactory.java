package com.statement.commerce.dao.mongo.factory;


import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Country;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.core.Provence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MerchantFactory
{
  private static final Log LOG                 = LogFactory.getLog(MerchantFactory.class);
  private static int merchantIdGenerator = 0;
  private static int addressIdGenerator = 0;

  public static Merchant getNewMerchant()
  {
    return buildSimpleMerchant();
  }

  public static Merchant getMerchantWithChild()
  {
    Merchant base = buildSimpleMerchant();
    Merchant child = new Merchant();
    copyMerchant(base, child);
    String childMerchantId = child.getId() + " - 1";
    child.setId(childMerchantId);

    HashMap<String, Merchant> childMap = new HashMap<>();
    childMap.put(childMerchantId, child);
    base.setChildMerchants(childMap);

    return base;
  }

  private static Merchant buildSimpleMerchant()
  {
    int localAddressId = addressIdGenerator++;
    int localMerchantId = merchantIdGenerator++;

    Address address = new Address();

    address.setId(String.valueOf(localAddressId));
    address.setName("Address Name " + localAddressId);
    address.setAddress1(localAddressId + " Hollywood Ave");
    address.setCity("Portland");
    address.setCountry(Country.US);
    address.setProvence(Provence.OREGON);
    address.setZipCode("97212");
    Merchant merchant = new Merchant();
    merchant.setBillingAddress(address);
    merchant.setId(String.valueOf(localMerchantId));
    merchant.setName("testMerchant " + localMerchantId);

    return merchant;
  }

  private static void copyMerchant(Merchant baseObject, Merchant targetObject)
  {
      List<Field> fieldList = new ArrayList<>();

      fieldList.addAll(Arrays.asList(baseObject.getClass().getDeclaredFields()));

      if (baseObject.getClass().equals(targetObject.getClass()))
      {
        fieldList.addAll(Arrays.asList(baseObject.getClass().getSuperclass().getDeclaredFields()));
      }

      for (Field field : fieldList)
      {
        boolean isAccessible = field.isAccessible();

        if (!isAccessible)
        {
          field.setAccessible(true);
        }

        try
        {
          field.set(targetObject, field.get(baseObject));
        }
        catch (IllegalAccessException iae)
        {
          // we don't really care about this exception here
          if (LOG.isTraceEnabled())
          {
            LOG.trace("Caught IllegalAccessException copying OrderLineItem");
          }
        }
        catch (IllegalArgumentException iae)
        {
          // we don't really care about this exception here
          if (LOG.isTraceEnabled())
          {
            LOG.trace("Caught IllegalArgumentException copying OrderLineItem");
          }
        }

        // reset the field accessibility
        if (!isAccessible)
        {
          field.setAccessible(false);
        }

    }
  }
}
