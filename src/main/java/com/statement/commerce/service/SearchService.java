package com.statement.commerce.service;

import com.statement.commerce.dao.SearchDao;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 8/18/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SearchService
{
  private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);
  @Autowired
  @Qualifier("JestSearchDao")
  private SearchDao searchDao;

  /**
   * Execute a free text search. NOTE: Facets have not yet been integrated here.
   * @param criteria the search criteria object
   * @return the Search results
   */
  public SearchResults<Product> termSearch(SearchCriteria criteria)
  {
    if(LOG.isDebugEnabled())
    {
      LOG.debug("Search criteria is as follows [" + criteria + ']');
    }

    return searchDao.termSearch(criteria);
  }

  public Product[] getProductsById(String... ids)
  {
    return searchDao.getByIds(ids);
  }
}
