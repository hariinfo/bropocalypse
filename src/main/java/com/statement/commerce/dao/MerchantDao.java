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

  /**
   * Does a merchant with the provided <code>merchantId</code> exist
   * @param merchantId the id of the merchant in question
   * @return true if the merchant exist; false otherwise
   */
  boolean merchantExists(String merchantId);
}
