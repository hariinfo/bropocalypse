package com.statement.commerce.dao;

import com.statement.commerce.model.core.Address;

public interface MerchantDao<T> extends GenericDao<T>
{
  void updateAddress(String id, Address address);
}
