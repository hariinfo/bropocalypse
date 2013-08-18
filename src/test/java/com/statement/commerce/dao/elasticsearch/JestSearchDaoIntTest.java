package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import io.searchbox.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/23/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class JestSearchDaoIntTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  private JestSearchDao dao;

  @Test(groups = "int")
  public void testTermSearch()
  {
    String term = "TEAM SP*";
    int maxResults = 50;
    SearchCriteria criteria = new SearchCriteria();
    criteria.setSearchTerm(term);
    criteria.setMaxRows(maxResults);
    criteria.setStartIndex(0);

    SearchResults<Product> results = dao.termSearch(criteria);

    Assert.assertNotNull(results, "the search result wrapper object should never be null");
    // uncomment once we have total result count set by the DAO
//    Assert.assertTrue(results.getResultCount() > 0);
    Assert.assertFalse(results.getResults().isEmpty(), "the results list should not be empty");
    Assert.assertEquals(results.getResults().size(), maxResults, "the result set size should match");
  }

}
