package com.statement.commerce.dao.mongo;

import com.statement.commerce.dao.DataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/24/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public abstract class MongoDataConfig implements DataConfig
{
  private static final String HTTP_PROTOCAL = "http://";
  private static final String COLON = ":";
  @Value("${mongodb.port}")
  private String mongoHost;
  @Value("${mongodb.host}")
  private String mongoPort;
  private String serverUrl;
  @Autowired
  private MongoDao mongoDao;

  @Override
  public String getServerUrl()
  {
    if(null == serverUrl)
    {
      serverUrl = HTTP_PROTOCAL + mongoHost + COLON + mongoPort;
    }
    return serverUrl;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void setServerUrl(String serverUrl)
  {
    this.serverUrl = serverUrl;
  }

  public String getMongoHost()
  {
    return mongoHost;
  }

  public void setMongoHost(String mongoHost)
  {
    this.mongoHost = mongoHost;
  }

  public String getMongoPort()
  {
    return mongoPort;
  }

  public void setMongoPort(String mongoPort)
  {
    this.mongoPort = mongoPort;
  }

  public MongoTemplate getMongoTemplate()
  {
    return mongoDao.getMongoTemplate();
  }

  public abstract String getDataFile();

  public abstract void setDataFile(String dataFile);
}
