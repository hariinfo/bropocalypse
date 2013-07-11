package com.statement.commerce.dao;

import com.statement.commerce.model.core.Address;

public interface MerchantDao<T> extends GenericDao<T>
{
  /**
   * Update the merchant address
   * @param id the id of the merchant
   * @param address the address data to be saved/updated to the merchant.
   */
  void updateAddress(String id, Address address);

  boolean merchantExists(String merchantId);
}
