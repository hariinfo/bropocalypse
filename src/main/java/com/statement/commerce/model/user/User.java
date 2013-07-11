package com.statement.commerce.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.statement.commerce.model.core.Address;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import static com.statement.commerce.dao.mongo.MongoUserDao.USER_COLLECTION;

@Document(collection = USER_COLLECTION)
public class User implements Serializable
{
  @Id
  private String id;
  @JsonIgnore
  private String sourceSystemId;
  private String loginName;
  private String firstName;
  private String lastName;
  private String middleName;
  private Locale locale;
  private TimeZone timeZone;
  private List<String> catalogIds;
  private List<Role> roles = new ArrayList<>();
  private Address billingAddress;
  private Address shippingAddress;
  private String merchantId;
  private String email;

  public User()
  {
  }

  public User(Role... roles)
  {
    this.roles.addAll(Arrays.asList(roles));
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getSourceSystemId()
  {
    return sourceSystemId;
  }

  public void setSourceSystemId(String sourceSystemId)
  {
    this.sourceSystemId = sourceSystemId;
  }

  public String getLoginName()
  {
    return loginName;
  }

  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getMiddleName()
  {
    return middleName;
  }

  public void setMiddleName(String middleName)
  {
    this.middleName = middleName;
  }

  public Locale getLocale()
  {
    return locale;
  }

  public void setLocale(Locale locale)
  {
    this.locale = locale;
  }

  public TimeZone getTimeZone()
  {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone)
  {
    this.timeZone = timeZone;
  }

  public List<Role> getRoles()
  {
    return roles;
  }

  public void setRoles(List<Role> roles)
  {
    this.roles = roles;
  }

  public String getMerchantId()
  {
    return merchantId;
  }

  public void setMerchantId(String merchantId)
  {
    this.merchantId = merchantId;
  }

  public List<String> getCatalogIds()
  {
    return catalogIds;
  }

  public void setCatalogIds(List<String> catalogIds)
  {
    this.catalogIds = catalogIds;
  }

  public Address getBillingAddress()
  {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress)
  {
    this.billingAddress = billingAddress;
  }

  public Address getShippingAddress()
  {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress)
  {
    this.shippingAddress = shippingAddress;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof User))
    {
      return false;
    }

    User that = (User) o;

    if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
    {
      return false;
    }
    if (id != null ? !id.equals(that.id) : that.id != null)
    {
      return false;
    }
    if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
    {
      return false;
    }
    if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null)
    {
      return false;
    }
    if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null)
    {
      return false;
    }
    if (sourceSystemId != null ? !sourceSystemId.equals(that.sourceSystemId) : that.sourceSystemId != null)
    {
      return false;
    }
    if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
    {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null)
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
    result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
    result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);

    return result;
  }

  @Override
  public String toString()
  {
    return ReflectionToStringBuilder.toString(this);
  }
}
