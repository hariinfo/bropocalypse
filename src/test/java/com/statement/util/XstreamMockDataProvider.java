package com.statement.util;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 2/21/13
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class XstreamMockDataProvider
{
  private XstreamMockDataProvider() {}

  /**
   * Writes the provided <code>object</code> out in XML file format.
   *
   * @param   object    the data to be write in XML format
   * @param   fileName  the file name
   *
   * @throws  IllegalArgumentException  thrown if the data file cannot be written.
   */
  public static void writeData(Object object, String fileName)
  {
    try
    {
      XStream xstream = new XStream();
      File file    = new File(fileName);

      xstream.toXML(object, new java.io.FileWriter(file));
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException(e);
    }
  }

  public static String writeData(Object object)
  {
    XStream xstream = new XStream();
    StringWriter sw      = new StringWriter();

    xstream.toXML(object, sw);

    return sw.toString();
  }

  /**
   * Reads the specified file in as XML and converts it to an <code>Object.</code>
   *
   * @param   fileName  the name of the persistent XML data file.
   *
   * @return  the data object
   *
   * @throws  IllegalArgumentException  thrown if the data file cannot be read.
   */
  public static Object readData(String fileName)
  {
    try
    {
      InputStreamReader reader  = new InputStreamReader(XstreamMockDataProvider.class.getResourceAsStream(fileName));
      XStream xstream = new XStream();

      return xstream.fromXML(reader);
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException(e);
    }
  }
}
