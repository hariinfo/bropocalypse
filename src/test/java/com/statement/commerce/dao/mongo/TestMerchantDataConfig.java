package com.statement.commerce.dao.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Config object used to parameters needed for loading merchant test data
 * User: dedrick
 * Date: 7/24/13
 * Time: 5:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TestMerchantDataConfig extends MongoDataConfig
{
  @Value(("${test.merchantdatafile}"))
  private String dataFile;

  @Override
  public String getDataFile()
  {
    return dataFile;
  }

  @Override
  public void setDataFile(String dataFile)
  {
    this.dataFile = dataFile;
  }
}
