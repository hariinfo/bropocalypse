package com.statement.commerce.model.product;

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
  private String id;
  private ProductType productType = ProductType.PRODUCT;
//  private ProductDetail detail = new ProductDetail();
  private String externalSystemId;
  private String productName;
  private Double retailPrice;
  private Double wholesalePrice;
  private List<ProductSize> sizes = new ArrayList<>();
  private Map<String, Object> customAttributes = new HashMap<>();

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
//
//  public ProductDetail getDetail()
//  {
//    return detail;
//  }
//
//  public void setDetail(ProductDetail detail)
//  {
//    this.detail = detail;
//  }

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

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String productName)
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
    return result;
  }
}
