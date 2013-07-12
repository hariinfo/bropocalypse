package com.statement.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/11/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util
{
  private static final Log LOG = LogFactory.getLog(Util.class);

  /**
   * Static method to copy fields from one bean to another
   * @param baseObject the base object
   * @param targetObject target object
   */
  public static void copyPojo(Object baseObject, Object targetObject)
  {
    List<Field> fieldList = new ArrayList<>();

    fieldList.addAll(Arrays.asList(baseObject.getClass().getDeclaredFields()));

    if (baseObject.getClass().equals(targetObject.getClass()))
    {
      fieldList.addAll(Arrays.asList(baseObject.getClass().getSuperclass().getDeclaredFields()));
    }

    for (Field field : fieldList)
    {
      boolean isAccessible = field.isAccessible();

      if (!isAccessible)
      {
        field.setAccessible(true);
      }

      try
      {
        field.set(targetObject, field.get(baseObject));
      }
      catch (IllegalAccessException iae)
      {
        // we don't really care about this exception here
        if (LOG.isTraceEnabled())
        {
          LOG.trace("Caught IllegalAccessException copying OrderLineItem");
        }
      }
      catch (IllegalArgumentException iae)
      {
        // we don't really care about this exception here
        if (LOG.isTraceEnabled())
        {
          LOG.trace("Caught IllegalArgumentException copying OrderLineItem");
        }
      }

      // reset the field accessibility
      if (!isAccessible)
      {
        field.setAccessible(false);
      }

    }
  }
}
