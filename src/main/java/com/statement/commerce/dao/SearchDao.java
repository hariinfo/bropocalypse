package com.statement.commerce.dao;

import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchResults;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/17/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchDao<SearchCriteria>
{
  SearchResults<Product> search(SearchCriteria criteria);
  SearchResults<Product> termSearch(SearchCriteria criteria);
}
