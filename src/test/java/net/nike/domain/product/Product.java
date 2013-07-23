package net.nike.domain.product;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/** Created with IntelliJ IDEA. User: dboyd2 Date: 7/9/12 Time: 1:51 PM To change this template use File | Settings | File Templates. */
public class Product
{
  private String                        id;
  private BigDecimal                    retailPrice;
  private BigDecimal                    wholesalePrice;
  private String                        style;
  private String                        color;
  private Map<String, ProductAttribute> attributeMap = new HashMap<String, ProductAttribute>();
  private ProductSizeRun sizeRun;
  private String                        colorName;
  private String                        styleName;
  private Division                      division;

  public Product() {}

  public Product(String style, String color, ProductSizeRun sizeRun)
  {
    this.style   = style;
    this.color   = color;
    this.sizeRun = sizeRun;
  }

  // -------------------------- OTHER METHODS --------------------------
  private void addAttribute(ProductAttribute productAttribute)
  {
    attributeMap.put(productAttribute.getName(), productAttribute);
  }

  private Collection<ProductAttribute> getProductAttributes()
  {
    return attributeMap.values();
  }

  public String getStyleColor()
  {
    return style + '-' + color;
  }

  // ------------------------ CANONICAL METHODS ------------------------
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

    if ((color != null) ? (!color.equals(product.color))
                        : (product.color != null))
    {
      return false;
    }

    if ((style != null) ? (!style.equals(product.style))
                        : (product.style != null))
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = (style != null) ? style.hashCode()
                                 : 0;

    result = (31 * result) + ((color != null) ? color.hashCode()
                                              : 0);

    return result;
  }

  // --------------------- GETTER / SETTER METHODS ---------------------
  public String getColor()
  {
    return color;
  }

  public void setColor(String color)
  {
    this.color = color;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public BigDecimal getRetailPrice()
  {
    return retailPrice;
  }

  public void setRetailPrice(BigDecimal retailPrice)
  {
    this.retailPrice = retailPrice;
  }

  public ProductSizeRun getSizeRun()
  {
    return sizeRun;
  }

  public void setSizeRun(ProductSizeRun sizeRun)
  {
    this.sizeRun = sizeRun;
  }

  public String getStyle()
  {
    return style;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public BigDecimal getWholesalePrice()
  {
    return wholesalePrice;
  }

  public void setWholesalePrice(BigDecimal wholesalePrice)
  {
    this.wholesalePrice = wholesalePrice;
  }

  public String getColorName()
  {
    return colorName;
  }

  public void setColorName(String colorName)
  {
    this.colorName = colorName;
  }

  public String getStyleName()
  {
    return styleName;
  }

  public void setStyleName(String styleName)
  {
    this.styleName = styleName;
  }

  public Division getDivision()
  {
    return division;
  }

  public void setDivision(Division division)
  {
    this.division = division;
  }
}
