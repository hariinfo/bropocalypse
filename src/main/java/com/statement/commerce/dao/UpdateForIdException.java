package com.statement.commerce.dao;

public class UpdateForIdException extends  RuntimeException
{
  public UpdateForIdException(String msg)
  {
    super(msg);
  }

  public UpdateForIdException(Throwable throwable)
  {
    super(throwable);
  }

  public UpdateForIdException(String msg, Throwable throwable)
  {
    super(msg, throwable);
  }
}
