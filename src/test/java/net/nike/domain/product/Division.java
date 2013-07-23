package net.nike.domain.product;

/** Created with IntelliJ IDEA. User: dboyd2 Date: 7/11/12 Time: 2:48 PM To change this template use File | Settings | File Templates. */
public enum Division
{
  APRL("APPAREL", "Apparel"),
  FTWR("FOOTWEAR", "Footwear"),
  EQMT("EQUIPMENT", "Equipment");

  private String description;
  private String id;

  Division(String id, String description)
  {
    this.id          = id;
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }

  public String getId()
  {
    return id;
  }
}
