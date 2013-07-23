package com.statement.commerce.dao.elasticsearch;

import com.statement.commerce.dao.SearchDao;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.search.SearchCriteria;
import com.statement.commerce.model.search.SearchResults;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.indices.IndicesExists;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
  @Value("${elasticsearchurl}")
  private String elasticServerUrlAndPort;
  private JestClient client;
  @Value("${elasticsearchindex}")
  private String indexName;

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

  @PostConstruct
  public void init() throws Exception
  {
    // Configuration
    ClientConfig clientConfig = new ClientConfig.Builder(elasticServerUrlAndPort).multiThreaded(true).build();

    // Construct a new Jest client according to configuration via factory
    JestClientFactory factory = new JestClientFactory();
    factory.setClientConfig(clientConfig);
    client = factory.getObject();


    // check to see if our index exists, if not, create it.
    IndicesExists indicesExists = new IndicesExists.Builder().addIndex(indexName).build();
    client.execute(indicesExists);

    LOG.error("did the index exist? " + indicesExists.getData());

    Boolean indexExists = (Boolean)indicesExists.getData();

    if(!indexExists)
    {
      // create the index!
    }
  }
}
