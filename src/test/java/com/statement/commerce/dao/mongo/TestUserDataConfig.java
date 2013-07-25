package com.statement.commerce.dao.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Config object used to parameters needed for loading user test data
 * User: dedrick
 * Date: 7/24/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TestUserDataConfig extends MongoDataConfig
{
  @Value(("${test.userdatafile}"))
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
