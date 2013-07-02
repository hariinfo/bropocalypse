package com.statement.commerce.model.core;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 6/29/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Address implements Serializable
{
  private static final long serialVersionUID = -4942769027605648344L;
  private String id;
  private String name;
  private String secondaryName;
  private String address1;
  private String address2;
  private String address3;
  private String city;
  private Provence provence;
  private Country country;
  private String zipCode;

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

  public String getSecondaryName()
  {
    return secondaryName;
  }

  public void setSecondaryName(String secondaryName)
  {
    this.secondaryName = secondaryName;
  }

  public String getAddress1()
  {
    return address1;
  }

  public void setAddress1(String address1)
  {
    this.address1 = address1;
  }

  public String getAddress2()
  {
    return address2;
  }

  public void setAddress2(String address2)
  {
    this.address2 = address2;
  }

  public String getAddress3()
  {
    return address3;
  }

  public void setAddress3(String address3)
  {
    this.address3 = address3;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public Provence getProvence()
  {
    return provence;
  }

  public void setProvence(Provence provence)
  {
    this.provence = provence;
  }

  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Address))
    {
      return false;
    }

    Address address = (Address) o;

    if (address1 != null ? !address1.equals(address.address1) : address.address1 != null)
    {
      return false;
    }
    if (address2 != null ? !address2.equals(address.address2) : address.address2 != null)
    {
      return false;
    }
    if (address3 != null ? !address3.equals(address.address3) : address.address3 != null)
    {
      return false;
    }
    if (city != null ? !city.equals(address.city) : address.city != null)
    {
      return false;
    }
    if (country != null ? !country.equals(address.country) : address.country != null)
    {
      return false;
    }
    if (id != null ? !id.equals(address.id) : address.id != null)
    {
      return false;
    }
    if (provence != null ? !provence.equals(address.provence) : address.provence != null)
    {
      return false;
    }
    if (zipCode != null ? !zipCode.equals(address.zipCode) : address.zipCode != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (address1 != null ? address1.hashCode() : 0);
    result = 31 * result + (address2 != null ? address2.hashCode() : 0);
    result = 31 * result + (address3 != null ? address3.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (provence != null ? provence.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
    return result;
  }
}
