package com.statement.commerce.service;

import com.statement.commerce.dao.GenericDao;
import com.statement.commerce.dao.MerchantDao;
import com.statement.commerce.dao.mongo.MongoMerchantDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/1/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MerchantService
{
  @Autowired
  @Qualifier("MongoMerchantDao")
  private MerchantDao merchantDao;

  public void saveAddress(String id, Address address)
  {
    merchantDao.updateAddress(id, address);
  }

  public List<Merchant> getByIds(String... ids)
  {
    return merchantDao.getByIds(ids);
  }

  public List<Merchant> find(String nameSearchString)
  {
    return merchantDao.findByName(nameSearchString);
  }

  public void delete(String ids)
  {
    merchantDao.delete(ids);
  }

  public void save(Merchant merchant)
  {
    merchantDao.save(merchant);
  }
}
