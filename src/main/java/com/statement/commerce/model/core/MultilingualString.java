package com.statement.commerce.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 8/26/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultilingualString implements Serializable
{
  private static final String UNDERSCORE = "_";
  private static final long serialVersionUID = -4026866114584924370L;
  private String primaryString;
  private String primaryLocale;
  private Map<Locale, String> translatedStrings = new HashMap<Locale, String>();

  public MultilingualString()
  {
  }

  public MultilingualString(String defaultLocale, String defaultTranslation)
  {
    primaryLocale = defaultLocale;

    if(StringUtils.isEmpty(defaultLocale))
    {
      throw new IllegalArgumentException("A default local must be provided.");
    }

    String[] localeStrings = defaultLocale.split(UNDERSCORE);

    if(localeStrings.length != 2)
    {
      throw new IllegalArgumentException("Improper format provided. Expected language_Country combination only. Example: 'en_US'");
    }
    primaryString = defaultTranslation;
    translatedStrings.put(new Locale(localeStrings[0], localeStrings[1]), defaultTranslation);
  }

  public String getPrimaryString()
  {
    return primaryString;
  }

  public void setPrimaryString(String primaryString)
  {
    this.primaryString = primaryString;
  }

  public String getPrimaryLocale()
  {
    return primaryLocale;
  }

  @JsonIgnore
  public Locale getPrimaryLocaleAsLocale()
  {
    String[] localeStrings = primaryLocale.split(UNDERSCORE);
    return new Locale(localeStrings[0], localeStrings[1]);
  }

  public void setPrimaryLocale(String primaryLocale)
  {
    this.primaryLocale = primaryLocale;
  }

  public String getString(Locale locale)
  {
    return translatedStrings.get(locale);
  }

  public Map<Locale, String> getTranslatedStrings()
  {
    return Collections.unmodifiableMap(translatedStrings);
  }

  public void setTranslatedStrings(Map<Locale, String> translatedStrings)
  {
    //noinspection AssignmentToCollectionOrArrayFieldFromParameter
    this.translatedStrings = translatedStrings;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    MultilingualString that = (MultilingualString) o;

    if (primaryLocale != null ? !primaryLocale.equals(that.primaryLocale) : that.primaryLocale != null)
    {
      return false;
    }
    if (primaryString != null ? !primaryString.equals(that.primaryString) : that.primaryString != null)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = primaryString != null ? primaryString.hashCode() : 0;
    result = 31 * result + (primaryLocale != null ? primaryLocale.hashCode() : 0);
    return result;
  }

}
