package com.statement.commerce.dao.mongo.factory;

import com.statement.commerce.model.core.MultilingualString;
import com.statement.commerce.model.product.Catalog;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalogFactory
{
  private static final Logger LOG = LoggerFactory.getLogger(CatalogFactory.class);
  private static int catalogIdGenerator = 0;


  public static Catalog buildSimpleCatalog()
  {
    int localCatalogId = catalogIdGenerator++;

    Catalog catalog = new Catalog();
    MultilingualString catalogName = new MultilingualString("en_US","Test_Catalog_Name_" + localCatalogId);
    catalogName.putTranslation("ru_RU", "Имя_испытаний_каталог_" + localCatalogId);
    catalog.setCatalogName(catalogName);
    catalog.setId(String.valueOf(localCatalogId));

    LOG.info("Created mock catalog with ID [" + catalog.getId() + "]");
    return catalog;
  }

  public static Catalog buildCatalogWithProductIds(List<String> productIds)
  {
    Catalog catalog = buildSimpleCatalog();
    catalog.setProductIds(productIds);
    return catalog;
  }
}
