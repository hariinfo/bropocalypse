package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.dao.DataConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/23/13
 * Time: 10:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ProductDataConfig implements JestDataConfig
{
  @Value("${elasticsearchurl}")
  private String serverUrl;
  @Value("${elasticsearchproductindex}")
  private String indexName;
  @Value("${elasticsearchproductdatafile}")
  private String dataFile;
  @Value("${elasticsearchproducttype}")
  private String indexType;

  @Override
  public String getIndexName()
  {
    return indexName;
  }

  @Override
  public void setIndexName(String indexName)
  {
    this.indexName = indexName;
  }

  @Override
  public String getServerUrl()
  {
    return serverUrl;
  }

  @Override
  public void setServerUrl(String serverUrl)
  {
    this.serverUrl = serverUrl;
  }

  @Override
  public String getIndexType()
  {
    return indexType;
  }

  @Override
  public void setIndexType(String indexType)
  {
    this.indexType = indexType;
  }
}
