package com.statement.commerce.dao;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 8/7/13
 * Time: 10:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataException extends RuntimeException
{
  private static final long serialVersionUID = -6099532586837366760L;

  public DataException(String msg)
  {
    super(msg);
  }

  public DataException(Throwable throwable)
  {
    super(throwable);
  }

  public DataException(String msg, Throwable throwable)
  {
    super(msg, throwable);
  }
}
