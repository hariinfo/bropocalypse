package com.statement.commerce.controller;

import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import com.statement.commerce.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.statement.commerce.controller.ControllerConstants.ID_PATH_VARIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 8/18/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SearchController
{
  private static final Logger LOG = LoggerFactory.getLogger(MerchantController.class);
  private static final String COMMA = ",";
  private static final String SEARCH_BASE = ControllerConstants.CURRENT_VERSION + "productsearch";
  private static final String PRODUCT_BY_ID = SEARCH_BASE + "/{ids}";
  private static final String PRODUCT_BY_TERM = SEARCH_BASE + "/termsearch/{term}";

  @Autowired
  private SearchService searchService;

  @RequestMapping(value  = PRODUCT_BY_ID, method = GET)
  @ResponseBody
  public Product[] findProductsById(@PathVariable("ids") String ids)
  {
    if(StringUtils.isEmpty(ids))
    {
      throw new IllegalArgumentException("No ids provided");
    }

    String[] idArray = ids.split(COMMA);
    Product[] products = searchService.getProductsById(idArray);

    return products;
  }

  @RequestMapping(value  = PRODUCT_BY_TERM, method = GET)
  @ResponseBody
  public SearchResults<Product> freeTextSearch(@PathVariable("term") String term, @RequestParam(value = "maxresults", required = false) int maxResults)
  {
    if(StringUtils.isEmpty(term))
    {
      throw new IllegalArgumentException("The search term cannot be empty");
    }

    SearchCriteria criteria = new SearchCriteria();
    criteria.setSearchTerm(term);

    if(maxResults > 0)
    {
      criteria.setMaxRows(maxResults);
    }

    return searchService.termSearch(criteria);
  }

  @RequestMapping(value  = SEARCH_BASE, method = POST)
  @ResponseBody
  public SearchResults<Product> search(@RequestBody SearchCriteria criteria)
  {
    if(null == criteria)
    {
      throw new IllegalArgumentException("The POST value was null.");
    }

    return searchService.termSearch(criteria);
  }
}
