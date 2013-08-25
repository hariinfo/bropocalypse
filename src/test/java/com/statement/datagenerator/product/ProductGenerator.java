package com.statement.datagenerator.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.commerce.model.product.Product;
import com.statement.commerce.model.product.ProductSize;
import com.statement.util.XstreamMockDataProvider;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.testng.annotations.Test;

/**
 * Dummy class used to convert raw XML data into our product model and then into json
 * User: dedrick
 * Date: 7/13/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class ProductGenerator
{
  private String[] apparelSizes = new String[]{"XS", "S", "M", "L", "XL", "XXL"};

  public Product convert(net.nike.domain.product.Product nikeProduct)
  {
    Product product = new Product();
    product.setExternalSystemId(nikeProduct.getId());
    product.setRetailPrice(getDoubleFromBigDecimal(nikeProduct.getRetailPrice()));
    product.setWholesalePrice(getDoubleFromBigDecimal(nikeProduct.getWholesalePrice()));
    product.setProductName(nikeProduct.getStyleName());
    product.addCustomAtrribute("colorName", nikeProduct.getColorName());
    product.addCustomAtrribute("color" ,nikeProduct.getColor());
    product.addCustomAtrribute("style", nikeProduct.getStyle());
    product.addCustomAtrribute("division", nikeProduct.getDivision().name());
    String[] colorFields = nikeProduct.getColorName().split("/");
    product.setPrimaryColor(colorFields[0]);
    List<ProductSize> sizes = new ArrayList<>();
    product.setSizes(sizes);

    for(net.nike.domain.product.ProductSize nikeSize : nikeProduct.getSizeRun().getSizes())
    {
      ProductSize size = new ProductSize();
      size.setUpc(nikeSize.getUpc());
      size.setSize(nikeSize.getSize());
      size.setDisplaySize(nikeSize.getAltSize());
      size.setDisplaySequence(nikeSize.getDisplaySequence());
      sizes.add(size);
    }

    return product;
  }

  public Double getDoubleFromBigDecimal(BigDecimal price)
  {
    if(null == price)
    {
      return null;
    }

    return price.doubleValue();
  }

  @Test(groups = "dataGenerator")
  public void generateApparelDataFile()
  {
    List<net.nike.domain.product.Product> nikeProducts = (List<net.nike.domain.product.Product>)XstreamMockDataProvider.readData("/sample_nike_products.xml");

    List<Product> allProducts = new ArrayList(nikeProducts.size());
    for(net.nike.domain.product.Product nikeProduct : nikeProducts)
    {
      Product product = convert(nikeProduct);
      allProducts.add(product);
    }



//    Product product = new Product();
//    product.setExternalSystemId("cat260470195-111432-011");
//    product.setProductName("FOUR INCH BAGGY SHORT");
//    product.setRetailPrice(50.50d);
//    product.setWholesalePrice(22.95d);
//
//    product.setSizes(generateApparelSizes(4, false, product));
    ObjectMapper m = new ObjectMapper();

    try(FileOutputStream FOS = new FileOutputStream("apparelData.json"))
    {
      for(Product product : allProducts)
      {
        String productString = m.writeValueAsString(product);
        FOS.write(productString.getBytes());
        FOS.write("\n".getBytes());
      }
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
  }

  private List<ProductSize> generateApparelSizes(int numSize, boolean overridePrice, Product product)
  {
    if(numSize < 1)
    {
      return null;
    }

    List<ProductSize> sizes = new ArrayList<>();
    for(int i = 0; i < numSize || i < apparelSizes.length; ++i)
    {
      ProductSize size = new ProductSize();
      size.setSize(apparelSizes[i]);
      if(overridePrice)
      {
        size.setRetailPrice(product.getRetailPrice() + i);
        size.setWholesalePrice(product.getWholesalePrice() + i);
      }

      size.setUpc(UUID.randomUUID().toString());
      sizes.add(size);
    }

    return sizes;
  }
}

/*
  <net.nike.domain.product.Product>
    <id>cat260470195-111432-011</id>
    <retailPrice>28</retailPrice>
    <wholesalePrice>14</wholesalePrice>
    <style>111432</style>
    <color>011</color>
    <attributeMap/>
    <sizeRun>
      <sizes class="linked-hash-set">
        <net.nike.domain.product.ProductSize>
          <id>cat260470195-111432-011</id>
          <size>S</size>
          <altSize>S</altSize>
          <upc>00883153895361</upc>
          <displaySequence>8</displaySequence>
        </net.nike.domain.product.ProductSize>
        <net.nike.domain.product.ProductSize>
          <id>cat260470195-111432-011</id>
          <size>M</size>
          <altSize>M</altSize>
          <upc>00883153895378</upc>
          <displaySequence>9</displaySequence>
        </net.nike.domain.product.ProductSize>
        <net.nike.domain.product.ProductSize>
          <id>cat260470195-111432-011</id>
          <size>L</size>
          <altSize>L</altSize>
          <upc>00883153895385</upc>
          <displaySequence>10</displaySequence>
        </net.nike.domain.product.ProductSize>
        <net.nike.domain.product.ProductSize>
          <id>cat260470195-111432-011</id>
          <size>XL</size>
          <altSize>XL</altSize>
          <upc>00883153895392</upc>
          <displaySequence>11</displaySequence>
        </net.nike.domain.product.ProductSize>
        <net.nike.domain.product.ProductSize>
          <id>cat260470195-111432-011</id>
          <size>XXL</size>
          <altSize>XXL</altSize>
          <upc>00883153895408</upc>
          <displaySequence>12</displaySequence>
        </net.nike.domain.product.ProductSize>
      </sizes>
    </sizeRun>
    <colorName>Black/White</colorName>
    <styleName>FOUR INCH BAGGY SHORT</styleName>
    <division>APRL</division>
  </net.nike.domain.product.Product>

 */
