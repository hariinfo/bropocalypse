package com.statement.commerce.model.search;

import com.statement.commerce.model.user.User;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchCriteria implements Serializable
{
  private static final String[] TERM_FIELDS = {"productName.translatedStrings.*","colorName","upc","catalogId"};
  private static final long serialVersionUID = -7986954969909435565L;
  private User user;
  private String searchTerm = "";
  private int maxRows          = 10;
  private int startIndex       = 0;


  public static String[] getTermFields()
  {
    return TERM_FIELDS;
  }

  public String getSearchTerm()
  {
    return searchTerm;
  }

  public void setSearchTerm(String searchTerm)
  {
    this.searchTerm = searchTerm;
  }

  /**
   * Returns the maximum number of rows to return from the search using this SearchCritera.
   *
   * @return  maxiumber number of rows to return in the search results
   */
  public int getMaxRows()
  {
    return maxRows;
  }

  /**
   * Sets the maximum number of rows to return from the search using this SearchCritera.
   *
   * @param  maxRows  - maximum number of rows to return in the search results
   */
  public void setMaxRows(int maxRows)
  {
    this.maxRows = maxRows;
  }

  /**
   * Returns the start index on the result set from which to return the rows The default is zero.
   */
  public int getStartIndex()
  {
    return startIndex;
  }

  /**
   * Sets the start index on the result set from which to return the rows. The default is zero.
   */
  public void setStartIndex(int startIndex)
  {
    this.startIndex = startIndex;
  }

  /** Returns the string representation of this SearchCriteria. */
  @Override
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * Returns the ISO language code this SearchCriteria. For example, "en", "es", "jp", etc. This language code is used to tell the search engine
   * what language to search in.
   *
   * @return  the ISO langague code
   */
  public String getLanguageCode()
  {
    if(null == getUser())
    {
      return null;
    }

    return getUser().getLocaleObject().getLanguage().toLowerCase();
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public int getPage()
  {
    return (int) Math.floor(startIndex / maxRows);
  }
}
