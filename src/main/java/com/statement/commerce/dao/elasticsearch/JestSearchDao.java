package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.dao.SearchDao;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/17/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class JestSearchDao implements SearchDao<SearchCriteria>
{
  private static final Log LOG = LogFactory.getLog(JestSearchDao.class);

  @Autowired
  private ProductDataConfig productDataConfig;
  @Value("${elasticsearchurl}")
  private String elasticServerUrlAndPort;
  @Value("${elasticsearchproductindex}")
  private String indexName;
  private JestClient client;

  @Override
  public SearchResults<Product> search(SearchCriteria searchCriteria)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public SearchResults<Product> termSearch(SearchCriteria searchCriteria)
  {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public void addProducts() throws Exception{

  }

  @PostConstruct
  public void init() throws Exception
  {
    // Configuration
    ClientConfig clientConfig = new ClientConfig.Builder(productDataConfig.getServerUrl()).multiThreaded(true).build();

    // Construct a new Jest client according to configuration via factory
    JestClientFactory factory = new JestClientFactory();
    factory.setClientConfig(clientConfig);
    client = factory.getObject();

    // check to see if our index exists, if not, create it.
    IndicesExists indicesExists = new IndicesExists.Builder().addIndex(productDataConfig.getIndexName()).build();
    JestResult result = client.execute(indicesExists);

    if(!result.isSucceeded())
    {
      // create the index!
      CreateIndex newIndex = new CreateIndex.Builder(productDataConfig.getIndexName()).build();
      JestResult createResult = client.execute(newIndex);

      if(!createResult.isSucceeded())
      {
        throw new Exception("Error creating ElasticSearch index [" + productDataConfig.getIndexName() + "].");
      }
    }
  }

  @PreDestroy
  public void cleanup()
  {
    if(null != client)
    {
      client.shutdownClient();
    }
  }
}
