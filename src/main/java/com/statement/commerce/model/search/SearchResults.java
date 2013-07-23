package com.statement.commerce.model.search;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/17/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchResults<E> implements Serializable
{
  private static final long serialVersionUID = -7399248032790375465L;
  // List of objects of type <E> returned from the search
  private List<E> results;
  private SearchCriteria searchCriteria;
  private Class<E> resultType;
  private long resultCount = -1;
  private int pageSize = 2;
  private int pageNumber = 1;

  public SearchResults()
  {
  }

  public SearchResults(Class<E> resultType)
  {
    this.resultType = resultType;
  }

  public Class<E> getResultType()
  {
    return resultType;
  }

  public void setResultType(Class<E> type)
  {
    this.resultType = type;
  }

  /**
   * Returns a list of result beans
   *
   * @return  a list of result beans
   */
  public List<E> getResults()
  {
    return results;
  }

  /**
   * Sets the list of result beans
   *
   * @param  results  list of result bean results
   */
  public void setResults(List<E> results)
  {
    this.results = results;
  }

  /**
   * Returns the total result count.
   *
   * @return
   */
  public long getResultCount()
  {
    return resultCount;
  }

  /**
   * Sets the total result count for this SearchResult.
   *
   * @param  count the result count
   */
  public void setResultCount(long count)
  {
    this.resultCount = count;
  }

  /**
   * Return the SearchCriteria that was used to get these search results.
   *
   * @return
   */
  public SearchCriteria getSearchCriteria()
  {
    return searchCriteria;
  }

  /**
   * Sets the SearchCriteria that was used to get these search results.
   *
   * @param  searchCriteria
   */
  public void setSearchCriteria(SearchCriteria searchCriteria)
  {
    this.searchCriteria = searchCriteria;
  }

  @Override
  public String toString()
  {
    return (new ReflectionToStringBuilder(this)).toString();
  }

  public int getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(int pageSize)
  {
    this.pageSize = pageSize;
  }

  public int getPageNumber()
  {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber)
  {
    this.pageNumber = pageNumber;
  }

  public void resetPageNumber()
  {
    this.pageNumber = 1;
  }
}
