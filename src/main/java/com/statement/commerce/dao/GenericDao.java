package com.statement.commerce.dao;

import java.util.List;


public interface GenericDao<T>
{
  void save(T t);

  void delete(String... ids);

  List<T> getByIds(String... ids);

  List<T> findByName(String name);
}
