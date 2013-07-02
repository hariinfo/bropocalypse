package com.statement.commerce.model.core;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 6/29/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectForIdNotFoundException extends RuntimeException
{
  public ObjectForIdNotFoundException(String msg)
  {
    super(msg);
  }

  public ObjectForIdNotFoundException(Throwable throwable)
  {
    super(throwable);
  }

  public ObjectForIdNotFoundException(String msg, Throwable throwable)
  {
    super(msg, throwable);
  }
}
