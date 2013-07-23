package com.statement.commerce.model.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/12/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductDetail
{
  private String id;
  private String externalSystemId;
  private String productName;
  private double retailPrice;
  private double wholesalePrice;
  private List<ProductSize> sizes = new ArrayList<>();


}
