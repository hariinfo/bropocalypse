package com.statement.commerce.dao;

import com.statement.commerce.model.core.MultilingualString;
import com.statement.commerce.model.product.Catalog;
import java.util.List;


public interface CatalogDao<T>  extends GenericDao<T>
{
  List<Catalog> getCatalogByExternalId(String... externalIds);
  void updateCatalogName(String catalogId, MultilingualString catalogName);
  List<Catalog> getCatalogsByCustomerId(String customerId);
  List<String> getCatalogProducts(String catalogId);
}
