package com.statement.commerce.dao.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Config object used to parameters needed for loading product test data
 * User: dedrick
 * Date: 7/23/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TestProductDataConfig extends ProductDataConfig
{
  @Value(("${test.productdatafile}"))
  private String dataFile;


  public String getDataFile()
  {
    return dataFile;
  }

  public void setDataFile(String dataFile)
  {
    this.dataFile = dataFile;
  }
}
