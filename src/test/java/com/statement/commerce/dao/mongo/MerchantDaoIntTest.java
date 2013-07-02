package com.statement.commerce.dao.mongo;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.factory.MerchantFactory;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Country;
import com.statement.commerce.model.core.Merchant;
import com.statement.commerce.model.core.Provence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class MerchantDaoIntTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  private MerchantDao merchantDao;
  private List<Merchant> deleteList = new ArrayList<>();

  @AfterTest(groups = "int")
  public void testCleanup()
  {
    List<String> ids = new ArrayList<>();
    for(Merchant merchant : deleteList)
    {
      ids.add(merchant.getId());
    }

    merchantDao.delete(ids.toArray(new String[ids.size()]));
    ids.clear();
  }

  @Test(groups = "int")
  public void testFindByName()
  {
    // setup
    Merchant merchant = MerchantFactory.getNewMerchant();
    merchant.setId(null);
    deleteList.add(merchant);
    merchantDao.save(merchant);

    // HARCODED substring, which isn't great for now
    int endIndex = merchant.getName().length() - 5;
    while(endIndex <= 0)
    {
      endIndex++;
    }

    String name = merchant.getName().substring(0, endIndex);
    List<Merchant> merchants = merchantDao.findByName(name);

    Assert.assertNotNull(merchant);
    Assert.assertTrue(merchants.size() > 0);

    // there should be exactly 1 match
    Merchant retrievedMerchant = merchants.get(0);
    Assert.assertEquals(retrievedMerchant, merchant, "The merchants should have been the same!");
  }

  @Test(groups = "int")
  public void testGetById()
  {
    // setup
    Merchant merchant = MerchantFactory.getNewMerchant();
    merchant.setId(null);
    deleteList.add(merchant);
    merchantDao.save(merchant);

    Merchant retrievedMerchant = merchantDao.getByIds(merchant.getId()).get(0);

    Assert.assertNotNull(retrievedMerchant, "We should have found a merchant with id [" + merchant.getId() + "]");
    Assert.assertEquals(retrievedMerchant, merchant, "The merchants should have been the same!");
  }

  @Test(groups = "int")
  public void testSave()
  {
    Merchant merchant = MerchantFactory.getNewMerchant();
    deleteList.add(merchant);
    merchantDao.save(merchant);
  }

  @Test(groups = "int")
  public void testSaveAddress()
  {
    Merchant merchant = MerchantFactory.getNewMerchant();
    merchant.setId(null);
    deleteList.add(merchant);
    Address address = merchant.getBillingAddress();
    // remove the address
    merchant.setBillingAddress(null);

    // save the merchant
    merchantDao.save(merchant);

    Assert.assertNull(merchant.getBillingAddress(), "The billing address should be null");

    // now save the address
    merchantDao.updateAddress(merchant.getId(), address);

    // update our model merchant
    merchant.setBillingAddress(address);

    // read the merchant from the DB
    Merchant retrievedMerchant = merchantDao.getByIds(merchant.getId()).get(0);

    Assert.assertNotNull(retrievedMerchant, "We should have found a merchant with id [" + merchant.getId() + "]");
    Assert.assertEquals(retrievedMerchant, merchant, "The merchants should have been the same!");
  }
}
