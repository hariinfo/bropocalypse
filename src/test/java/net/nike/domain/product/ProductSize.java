package net.nike.domain.product;

/** Created with IntelliJ IDEA. User: dboyd2 Date: 7/9/12 Time: 3:35 PM To change this template use File | Settings | File Templates. */
public class ProductSize
{
  private String id;
  private String size;
  private String altSize;
  private String upc;
  private int    displaySequence;

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }

    if (!(o instanceof ProductSize))
    {
      return false;
    }

    ProductSize that = (ProductSize) o;

    if ((size != null) ? (!size.equals(that.size))
                       : (that.size != null))
    {
      return false;
    }

    if ((upc != null) ? (!upc.equals(that.upc))
                      : (that.upc != null))
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = (size != null) ? size.hashCode()
                                : 0;

    result = (31 * result) + ((upc != null) ? upc.hashCode()
                                            : 0);

    return result;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getSize()
  {
    return size;
  }

  public void setSize(String size)
  {
    this.size = size;
  }

  public String getAltSize()
  {
    return altSize;
  }

  public void setAltSize(String altSize)
  {
    this.altSize = altSize;
  }

  public String getUpc()
  {
    return upc;
  }

  public void setUpc(String upc)
  {
    this.upc = upc;
  }

  public int getDisplaySequence()
  {
    return displaySequence;
  }

  public void setDisplaySequence(int displaySequence)
  {
    this.displaySequence = displaySequence;
  }
}
