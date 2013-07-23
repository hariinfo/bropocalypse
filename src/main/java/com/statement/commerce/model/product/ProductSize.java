package com.statement.commerce.model.product;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/12/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductSize
{
  public static final String NO_SIZE = "NA";
  public static final String MISC = "MISC";
  public static final String ONE_SIZE_FITS_ALL = "OneSize";
  private String size;
  private String displaySize;
  private String upc;
  private double wholesalePrice;
  private double retailPrice;
  private int displaySequence;

  public static ProductSize getNoSize()
  {
    ProductSize size = new ProductSize();
    size.setSize(NO_SIZE);
    return size;
  }

  public static ProductSize getMiscSize()
  {
    ProductSize size = new ProductSize();
    size.setSize(MISC);
    return size;
  }

  public static ProductSize getOneSize()
  {
    ProductSize size = new ProductSize();
    size.setSize(ONE_SIZE_FITS_ALL);
    return size;
  }

  public String getSize()
  {
    return size;
  }

  public void setSize(String size)
  {
    this.size = size;
  }

  public String getDisplaySize()
  {
    return displaySize;
  }

  public void setDisplaySize(String displaySize)
  {
    this.displaySize = displaySize;
  }

  public String getUpc()
  {
    return upc;
  }

  public void setUpc(String upc)
  {
    this.upc = upc;
  }

  public double getWholesalePrice()
  {
    return wholesalePrice;
  }

  public void setWholesalePrice(double wholesalePrice)
  {
    this.wholesalePrice = wholesalePrice;
  }

  public double getRetailPrice()
  {
    return retailPrice;
  }

  public void setRetailPrice(double retailPrice)
  {
    this.retailPrice = retailPrice;
  }

  public int getDisplaySequence()
  {
    return displaySequence;
  }

  public void setDisplaySequence(int displaySequence)
  {
    this.displaySequence = displaySequence;
  }

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

    if (size != null ? !size.equals(that.size) : that.size != null)
    {
      return false;
    }

    if (upc != null ? !upc.equals(that.upc) : that.upc != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = size != null ? size.hashCode() : 0;
    result = 31 * result + (upc != null ? upc.hashCode() : 0);
    return result;
  }
}
