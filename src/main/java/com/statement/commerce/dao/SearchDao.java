package com.statement.commerce.dao;

import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchResults;

/**
 * Search DAO
 * User: dedrick
 * Date: 7/17/13
 * Time: 2:00 PM
 */
public interface SearchDao<SearchCriteria>
{
  SearchResults<Product> search(SearchCriteria criteria);
  SearchResults<Product> termSearch(SearchCriteria criteria);
}
