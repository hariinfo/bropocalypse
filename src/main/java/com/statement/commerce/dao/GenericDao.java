package com.statement.commerce.dao;

import java.util.List;


public interface GenericDao<T>
{
  /**
   * Save the provided object to the underlying data store
   * @param t
   */
  void save(T t);

  /**
   * Delete the stored data from the underlying data store
   * @param ids the ids of the data to be deleted.
   */
  void delete(String... ids);

  /**
   * Get the full data object identified by the provided ids
   * @param ids the ids of the desired data
   * @return the data as a list. The list may be empty, never null.
   */
  List<T> getByIds(String... ids);

  /**
   * Get the required data using a 'like' query. The query is executed against the 'name' field.
   * @param name the string to be used in the 'like' query
   * @return the data as a list. The list may be empty, never null.
   */
  List<T> findByName(String name);
}
