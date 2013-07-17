package com.statement.commerce.util;

import java.util.UUID;

public class UUIDGenerator
{
  private static final String NODE_ID = System.getProperty("node.id");
  private static final String HYPHEN = "-";

  public static String generateProductId()
  {
    return NODE_ID + HYPHEN + UUID.randomUUID().toString();
  }
}
