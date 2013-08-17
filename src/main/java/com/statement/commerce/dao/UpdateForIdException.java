package com.statement.commerce.dao;

public class UpdateForIdException extends  RuntimeException
{
  private static final long serialVersionUID = 3495293376541445211L;

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
