package com.statement.commerce.model.core;

/**
 * Created with IntelliJ IDEA.
 * User: dedrick
 * Date: 7/1/13
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Provence
{
  ALABAMA("AL", "Alabama"),
  ALASKA("AK","Alaska"),
  AMERICAN_SAMOA("AS","American Samoa"),
  ARIZONA("AZ","Arizona"),
  ARKANSAS("AR","Arkansas"),
  CALIFORNIA("CA","California"),
  COLORADO("CO","Colorado"),
  CONNECTICUT("CT","Connecticut"),
  DELAWARE("DE","Delaware"),
  DISTRICT_OF_COLUMBIA("DC","District of Columbia"),
  FEDERATED_STATES_OF_MICRONESIA("FM","Federated States of Micronesia"),
  FLORIDA("FL", "Florida"),
  GEORGIA("GA","Georgia"),
  GUAM("GU","Guam"),
  HAWAII("HI","Hawaii"),
  IDAHO("ID","Idaho"),
  ILLINOIS("IL","Illinois"),
  INDIANA("IN","Indiana"),
  IOWA("IA","Iowa"),
  KANSAS("KS","Kansas"),
  KENTUCKY("KY","Kentucky"),
  LOUISIANA("LA","Louisiana"),
  MAINE("ME","Maine"),
  MARSHALL_ISLANDS("MH","Marshall Islands"),
  MARYLAND("MD","Maryland"),
  MASSACHUSETTS("MA","Massachusetts"),
  MICHIGAN("MI","Michigan"),
  MINNESOTA("MN","Minnesota"),
  MISSISSIPPI("MS","Mississippi"),
  MISSOURI("MO","Missouri"),
  MONTANA("MT","Montana"),
  NEBRASKA("NE","Nebraska"),
  NEVADA("NV","Nevada"),
  NEW_HAMPSHIRE("NH","New Hampshire"),
  NEW_JERSEY("NJ","New Jersey"),
  NEW_MEXICO("NM","New Mexico"),
  NEW_YORK("NY","New York"),
  NORTH_CAROLINA("NC","North Carolina"),
  NORTH_DAKOTA("ND","North Dakota"),
  NORTHERN_MARIANA_ISLANDS("MP","Northern Mariana Islands"),
  OHIO("OH","Ohio"),
  OKLAHOMA("OK","Oklahoma"),
  OREGON("OR","Oregon"),
  PALAU("PW","Palau"),
  PENNSYLVANIA("PA","Pennsylvania"),
  PUERTO_RICO("PR","Puerto Rico"),
  RHODE_ISLAND("RI","Rhode Island"),
  SOUTH_CAROLINA("SC","South Carolina"),
  SOUTH_DAKOTA("SD", "South Dakota"),
  TENNESSEE("TN","Tennessee"),
  TEXAS("TX","Texas"),
  UTAH("UT","Utah"),
  VERMONT("VT","Vermont"),
  VIRGIN_ISLANDS("VI","Virgin Islands"),
  VIRGINIA("VA","Virginia"),
  WASHINGTON("WA","Washington"),
  WEST_VIRGINIA("WV","West Virginia"),
  WISCONSIN("WI","Wisconsin"),
  WYOMING("WY","Wyoming");

  private String code;
  private String description;

  Provence(String code, String description)
  {
    this.code = code;
    this.description = description;
  }

  public String getCode()
  {
    return code;
  }

  public String getDescription()
  {
    return description;
  }
}
