package com.statement.commerce.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.statement.commerce.model.core.MultilingualString;
import io.searchbox.annotations.JestId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;


/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/12/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Product
{
  @JestId
  public String id;
  private ProductType productType = ProductType.PRODUCT;
  private String externalSystemId;
  private MultilingualString productName;
  private Double retailPrice;
  private Double wholesalePrice;
  private String primaryColor;
  private List<ProductSize> sizes = new ArrayList<>();
  private Map<String, Object> customAttributes = new HashMap<>();
  private String url;
  private String category;
  private String catalogId;

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public ProductType getProductType()
  {
    return productType;
  }

  public void setProductType(ProductType productType)
  {
    this.productType = productType;
  }

  public void addCustomAtrribute(String key, Object value)
  {
    customAttributes.put(key, value);
  }

  public Object getCustomAttribute(String key)
  {
    return customAttributes.get(key);
  }

  public Map<String, Object> getCustomAttributes()
  {
    return customAttributes;
  }

  public void setCustomAttributes(Map<String, Object> customAttributes)
  {
    this.customAttributes = customAttributes;
  }

  public String getExternalSystemId()
  {
    return externalSystemId;
  }

  public void setExternalSystemId(String externalSystemId)
  {
    this.externalSystemId = externalSystemId;
  }

  @JsonIgnore
  public String getPrimaryProductName()
  {
    String name;
    if(null != productName)
    {
      name = productName.getPrimaryString();
    }
    else
    {
      name = null;
    }

    return name;
  }

//
//  public void setPrimaryProductName(String primaryProductName)
//  {
//    this.primaryProductName = primaryProductName;
//  }

  public MultilingualString getProductName()
  {
    return productName;
  }

  public void setProductName(MultilingualString productName)
  {
    this.productName = productName;
  }

  public Double getRetailPrice()
  {
    return retailPrice;
  }

  public void setRetailPrice(Double retailPrice)
  {
    this.retailPrice = retailPrice;
  }

  public Double getWholesalePrice()
  {
    return wholesalePrice;
  }

  public void setWholesalePrice(Double wholesalePrice)
  {
    this.wholesalePrice = wholesalePrice;
  }

  public List<ProductSize> getSizes()
  {
    return sizes;
  }

  public void setSizes(List<ProductSize> sizes)
  {
    this.sizes = sizes;
  }

  public String getPrimaryColor()
  {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor)
  {
    this.primaryColor = primaryColor;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getCategory()
  {
    return category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  public String getCatalogId()
  {
    return catalogId;
  }

  public void setCatalogId(String catalogId)
  {
    this.catalogId = catalogId;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Product))
    {
      return false;
    }

    Product product = (Product) o;

    if (externalSystemId != null ? !externalSystemId.equals(product.externalSystemId) : product.externalSystemId != null)
    {
      return false;
    }
    if (id != null ? !id.equals(product.id) : product.id != null)
    {
      return false;
    }
    if (productName != null ? !productName.equals(product.productName) : product.productName != null)
    {
      return false;
    }
    if (catalogId != null ? !catalogId.equals(product.catalogId) : product.catalogId != null)
    {
      return false;
    }
    if (productType != product.productType)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (productType != null ? productType.hashCode() : 0);
    result = 31 * result + (externalSystemId != null ? externalSystemId.hashCode() : 0);
    result = 31 * result + (productName != null ? productName.hashCode() : 0);
    result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
    return result;
  }
}
