package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.dao.DataConfig;

/**
 * Config object for a Jest DAO
 * User: dedrick
 * Date: 7/23/13
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface JestDataConfig extends DataConfig
{
  String getIndexName();

  void setIndexName(String indexName);

  String getIndexType();

  void setIndexType(String indexType);
}
