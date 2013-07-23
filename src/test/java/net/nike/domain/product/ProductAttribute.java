package net.nike.domain.product;

/** Created with IntelliJ IDEA. User: dboyd2 Date: 7/9/12 Time: 3:10 PM To change this template use File | Settings | File Templates. */
public class ProductAttribute
{
  private String id;
  private String name;
  private String value;

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

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }

    if (!(o instanceof ProductAttribute))
    {
      return false;
    }

    ProductAttribute that = (ProductAttribute) o;

    if ((name != null) ? (!name.equals(that.name))
                       : (that.name != null))
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    return (name != null) ? name.hashCode()
                          : 0;
  }
}
