package com.statement.commerce.dao.sql;

import com.statement.commerce.dao.MerchantDao;
import com.statement.commerce.model.core.Address;
import com.statement.commerce.model.core.Merchant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SqlMerchantDao")
public class SqlMerchantDao implements MerchantDao<Merchant>
{
  @Override
  public void updateAddress(String id, Address address)
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void save(Merchant merchant)
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void delete(String... ids)
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<Merchant> getByIds(String... ids)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<Merchant> findByName(String name)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
