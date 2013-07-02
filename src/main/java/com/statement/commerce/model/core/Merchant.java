package com.statement.commerce.model.core;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import static com.statement.commerce.dao.mongo.MerchantDao.MERCHANT_COLLECTION;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;


@Document(collection = MERCHANT_COLLECTION)
public class Merchant implements Serializable
{
  private static final long serialVersionUID = -4943956514804156244L;
  @Id
  private String id;
  private String sourceSystemId;
  private Address billingAddress;
  private HashMap<String, Merchant> childMerchants = new HashMap<>();
  private String parentEntityId;
  private String name;


  public Merchant()
  {
  }

  public Merchant(String id)
  {
    this.id = id;
  }

  public Merchant(String id, HashMap<String, Merchant> childMerchants)
  {
    this.childMerchants = childMerchants;
  }

  public boolean isParentMerchant()
  {
    return !childMerchants.isEmpty();
  }

  public Collection<Merchant> getAllChildMerchants()
  {
    return Collections.unmodifiableCollection(childMerchants.values());
  }

  public Merchant getChildMerchant(String id)
  {
    Merchant child = childMerchants.get(id);

    if(null == child)
    {
      throw new ObjectForIdNotFoundException("Child merchant with id[" + id + "] is not associated with this merchant");
    }

    return child;
  }

  public void setChildMerchants(HashMap<String, Merchant> childMerchants)
  {
    this.childMerchants = childMerchants;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Address getBillingAddress()
  {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress)
  {
    this.billingAddress = billingAddress;
  }

  public String getParentEntityId()
  {
    return parentEntityId;
  }

  public void setParentEntityId(String parentEntityId)
  {
    this.parentEntityId = parentEntityId;
  }

  public String getSourceSystemId()
  {
    return sourceSystemId;
  }

  public void setSourceSystemId(String sourceSystemId)
  {
    this.sourceSystemId = sourceSystemId;
  }

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
    if (!(o instanceof Merchant))
    {
      return false;
    }

    Merchant merchant = (Merchant) o;

    if (billingAddress != null ? !billingAddress.equals(merchant.billingAddress) : merchant.billingAddress != null)
    {
      return false;
    }
    if (childMerchants != null ? !childMerchants.equals(merchant.childMerchants) : merchant.childMerchants != null)
    {
      return false;
    }
    if (id != null ? !id.equals(merchant.id) : merchant.id != null)
    {
      return false;
    }
    if (parentEntityId != null ? !parentEntityId.equals(merchant.parentEntityId) : merchant.parentEntityId != null)
    {
      return false;
    }
    if (sourceSystemId != null ? !sourceSystemId.equals(merchant.sourceSystemId) : merchant.sourceSystemId != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (sourceSystemId != null ? sourceSystemId.hashCode() : 0);
    result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
    result = 31 * result + (childMerchants != null ? childMerchants.hashCode() : 0);
    result = 31 * result + (parentEntityId != null ? parentEntityId.hashCode() : 0);
    return result;
  }
}
