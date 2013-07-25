package com.statement.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.util.UUIDGenerator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.facet.Facets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/13/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProductController
{
  public static final String SYNC_PRODUCT = ControllerConstants.CURRENT_VERSION + "product/sync";


  @RequestMapping(value  = SYNC_PRODUCT, method = GET)
  @ResponseBody
  public void loadData()
  {
    // TEMP TEST CODE... to be  modified once I get all the elastic search stuff working with the Jest client
//Create Client
    Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
    TransportClient client = new TransportClient(settings);
    client = client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
//    return (Client) transportClient;


    DeleteIndexRequestBuilder deleteBuilder = client.admin().indices().prepareDelete("bropocalypse");
    deleteBuilder.execute().actionGet();

//Create Index and set settings and mappings

    String mapId = "{\"product\": { \"_id\": { \"path\": \"id\" } } }";
//    CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("bropocalypse");
    CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("bropocalypse").addMapping("product", mapId);
    createIndexRequestBuilder.execute().actionGet();


//    client.admin().indices().prepareCreate("pojoes").addMapping("pojo", mapId).execute().actionGet();

//Add documents



    byte[] x = null;

    List<String> productLines = null;

    try
    {
//      ProductController.class.getResource("apparelData.json").toURI();
//      x = Files.readAllBytes(FileSystems.getDefault().getPath("apparelData.json"));
      URL url = ProductController.class.getResource("/test/main/resources/apparelData.json");
      URI uri = url.toURI();

//      x = Files.readAllBytes(Paths.get(uri));

      productLines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
    }
    catch(URISyntaxException use)
    {
      use.printStackTrace();
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();

//      try
//      {
//        x = Files.readAllBytes(Paths.get("/apparelData.json"));
//      }
//      catch(IOException ioe1)
//      {
//        ioe1.printStackTrace();
//      }
    }

    Product tempProduct = null;

    ObjectMapper mapper = new ObjectMapper();
    try
    {
      IndexRequestBuilder indexRequestBuilder1 = client.prepareIndex("bropocalypse", "product");

      for(String pLine : productLines)
      {
        tempProduct = mapper.readValue(pLine, Product.class);
        tempProduct.setId(UUIDGenerator.generateProductId());
        indexRequestBuilder1.setSource(mapper.writeValueAsBytes(tempProduct));
        IndexResponse response = indexRequestBuilder1.execute().actionGet();
        System.out.println("GeneratedId = " + response.getId());
      }

//      tempProduct = mapper.readValue(x, Product.class);
//      tempProduct.setId(UUIDGenerator.generateProductId());
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
      throw new RuntimeException(ioe);
    }

//    XContentBuilder contentBuilder = jsonBuilder().startObject().prettyPrint();
//    contentBuilder.field("name", "jai");
//    contentBuilder.stopObject();
//    IndexRequestBuilder indexRequestBuilder = client.prepareIndex("bropocalypse", "product", tempProduct.getId());
////    indexRequestBuilder.setSource(x);
//    try
//    {
//      indexRequestBuilder.setSource(mapper.writeValueAsBytes(tempProduct));
//    }
//    catch(IOException ioe)
//    {
//      ioe.printStackTrace();
//      throw new RuntimeException(ioe);
//    }
//    IndexResponse response = indexRequestBuilder.execute().actionGet();
//System.out.println(response);
//System.out.println(response.getId());
//System.out.println(response.getIndex());
//System.out.println(response.getType());
//System.out.println(response.getVersion());
//
//
////    tempProduct.setId(response.getId());
////    indexRequestBuilder = client.prepareIndex("bropocalypse", "product", tempProduct.getId());
////
////    try
////    {
////      indexRequestBuilder.setSource(mapper.writeValueAsBytes(tempProduct));
////    }
////    catch(IOException ioe)
////    {
////      ioe.printStackTrace();
////      throw new RuntimeException(ioe);
////    }
//
//
////Get document
//    GetRequestBuilder getRequestBuilder = client.prepareGet("bropocalypse", "product", response.getId());
//    //getRequestBuilder.
//
//    //getRequestBuilder.setFields(new String[]{"name"});
//    GetResponse readResponse = getRequestBuilder.execute().actionGet();
//    try
//    {
//      Product p = mapper.readValue(readResponse.getSourceAsString(), Product.class);
//System.out.println(p);
//    }
//    catch(IOException ioe)
//    {
//      ioe.printStackTrace();
//    }
//

    SearchRequestBuilder builder = client.prepareSearch("bropocalypse").setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    SearchResponse response = builder.execute().actionGet();
    Facets facets = response.getFacets();
  }
}
