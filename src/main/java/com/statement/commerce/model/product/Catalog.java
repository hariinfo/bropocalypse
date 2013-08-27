package com.statement.commerce.model.product;

import com.statement.commerce.dao.mongo.MongoCatalogDao;
import com.statement.commerce.model.core.MultilingualString;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = MongoCatalogDao.CATALOG_COLLECTION)
public class Catalog
{
  @Id
  private String id;
  private String customerId;
  private MultilingualString catalogName;
  private String externalId;
  private List<String> productIds;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getCustomerId()
  {
    return customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public MultilingualString getCatalogName()
  {
    return catalogName;
  }

  public void setCatalogName(MultilingualString catalogName)
  {
    this.catalogName = catalogName;
  }

  public String getExternalId()
  {
    return externalId;
  }

  public void setExternalId(String externalId)
  {
    this.externalId = externalId;
  }

  public List<String> getProductIds()
  {
    return productIds;
  }

  public void setProductIds(List<String> productIds)
  {
    this.productIds = productIds;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Catalog))
    {
      return false;
    }

    Catalog catalog = (Catalog) o;

    if (customerId != null ? !customerId.equals(catalog.customerId) : catalog.customerId != null)
    {
      return false;
    }
    if (externalId != null ? !externalId.equals(catalog.externalId) : catalog.externalId != null)
    {
      return false;
    }
    if (id != null ? !id.equals(catalog.id) : catalog.id != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
    result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
    return result;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this);
  }
}
