package com.statement.commerce.dao.mongo;

import com.statement.commerce.context.AppContext;
import com.statement.commerce.context.LocalProfile;
import com.statement.commerce.dao.mongo.factory.CatalogFactory;
import com.statement.commerce.model.product.Catalog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ActiveProfiles(profiles = { "LocalEnv" })
@ContextConfiguration(classes = { AppContext.class, LocalProfile.class })
public class MongoCatalogDaoIntTest  extends AbstractTestNGSpringContextTests
{
  private List<Catalog> deleteList = new ArrayList<>();

  @Autowired
  private MongoCatalogDao mongoCatalogDao;


  @AfterMethod(groups = "int")
  public void testCleanup()
  {
    List<String> ids = new ArrayList<>();
    for(Catalog catalog : deleteList)
    {
      ids.add(catalog.getId());
    }

    mongoCatalogDao.delete(ids.toArray(new String[ids.size()]));
    ids.clear();
  }

  @Test(groups = "int")
  public void testSave()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    catalog.setId(null);
    deleteList.add(catalog);
    mongoCatalogDao.save(catalog);

    assertNotNull(catalog.getId());
  }

  @Test(groups = "int", enabled = false)
  public void testFindByName()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    deleteList.add(catalog);
    mongoCatalogDao.save(catalog);

    Catalog retrievedCatalog = mongoCatalogDao.findByName(catalog.getCatalogName().getPrimaryString()).get(0);

    assertNotNull(retrievedCatalog, "The catalog should not be null");
    assertEquals(retrievedCatalog, catalog, "The catalogs are not the same!");
  }

  @Test(groups = "int")
  public void testGetById()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    deleteList.add(catalog);
    mongoCatalogDao.save(catalog);

    Catalog retrievedCatalog = mongoCatalogDao.getByIds(catalog.getId()).get(0);

    assertNotNull(retrievedCatalog, "The catalog should not be null");
    assertEquals(retrievedCatalog, catalog, "The catalogs are not the same!");
  }

  @Test(groups = "int")
  public void testGetByExternalId()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    deleteList.add(catalog);
    catalog.setExternalId("shazamWow351");
    mongoCatalogDao.save(catalog);

    Catalog retrievedCatalog = mongoCatalogDao.getCatalogByExternalId(catalog.getExternalId()).get(0);

    assertNotNull(retrievedCatalog, "The catalog should not be null");
    assertEquals(retrievedCatalog, catalog, "The catalogs are not the same!");
  }

  @Test(groups = "int")
  public void testGetByCustomerId()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    deleteList.add(catalog);
    catalog.setExternalId("shazamWow351");
    catalog.setCustomerId("2435hbr3aj83nq9cnn381nd731");
    mongoCatalogDao.save(catalog);
    Catalog retrievedCatalog = mongoCatalogDao.getCatalogsByCustomerId(catalog.getCustomerId()).get(0);

    assertNotNull(retrievedCatalog, "The catalog should not be null");
    assertEquals(retrievedCatalog, catalog, "The catalogs are not the same!");
  }

  @Test(groups = "int")
  public void testGetCatalogProducts()
  {
    Catalog catalog = CatalogFactory.buildSimpleCatalog();
    catalog.setId(null);
    deleteList.add(catalog);
    List<String> productIds = Arrays.asList("9034nfhh47b","holla43","0498mfjr9nr3");
    catalog.setProductIds(productIds);

    mongoCatalogDao.save(catalog);

    assertNotNull(catalog.getId());

    List<String> retrievedIds = mongoCatalogDao.getCatalogProducts(catalog.getId());
    assertNotNull(retrievedIds, "The product id list should not be null");
    assertEquals(retrievedIds, productIds, "The lists should be the same");
  }
}
