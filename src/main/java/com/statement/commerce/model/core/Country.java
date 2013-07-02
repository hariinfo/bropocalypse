package com.statement.commerce.model.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum Country implements Serializable
{
  AF("AF", "Afghanistan"),
  AX("AX", "Aland Islands"),
  AL("AL", "Albania"),
  DZ("DZ", "Algeria"),
  AS("AS", "American Samoa"),
  AD("AD", "Andorra"),
  AO("AO", "Angola"),
  AI("AI", "Anguilla"),
  AQ("AQ", "Antarctica"),
  AG("AG", "Antigua and Barbuda"),
  AR("AR", "Argentina"),
  AM("AM", "Armenia"),
  AW("AW", "Aruba"),
  AU("AU", "Australia"),
  AT("AT", "Austria"),
  AZ("AZ", "Azerbaijan"),
  BS("BS", "Bahamas"),
  BH("BH", "Bahrain"),
  BD("BD", "Bangladesh"),
  BB("BB", "Barbados"),
  BY("BY", "Belarus"),
  BE("BE", "Belgium"),
  BZ("BZ", "Belize"),
  BJ("BJ", "Benin"),
  BM("BM", "Bermuda"),
  BT("BT", "Bhutan"),
  BO("BO", "Bolivia"),
  BA("BA", "Bosnia and herzegovina"),
  BW("BW", "Botswana"),
  BV("BV", "Bouvet_Island"),
  BR("BR", "Brazil"),
  IO("IO", "British Indian Ocean Territory"),
  BN("BN", "Brunei Darussalam"),
  BG("BG", "Bulgaria"),
  BF("BF", "Burkina Faso"),
  BI("BI", "Burundi"),
  KH("KH", "Cambodia"),
  CM("CM", "Cameroon"),
  CA("CA", "Canada"),
  CV("CV", "Cape Verde"),
  KY("KY", "Cayman Islands"),
  CF("CF", "Central African Republic"),
  TD("TD", "Chad"),
  CL("CL", "Chile"),
  CN("CN", "China"),
  CX("CX", "Christmas Island"),
  CC("CC", "Cocos Keeling Islands"),
  CO("CO", "Colombia"),
  KM("KM", "Comoros"),
  CG("CG", "Congo"),
  CD("CD", "The Democratic Republic Of Congo"),
  CK("CK", "Cook Islands"),
  CR("CR", "Costa Rica"),
  CI("CI", "Ivory Coast"),
  HR("HR", "Croatia"),
  CU("CU", "Cuba"),
  CY("CY", "Cyprus"),
  CZ("CZ", "Czech Republic"),
  DK("DK", "Denmark"),
  DJ("DJ", "Djibouti"),
  DM("DM", "Dominica"),
  DO("DO", "Dominican Republic"),
  EC("EC", "Ecuador"),
  EG("EG", "Egypt"),
  SV("SV", "El Salvador"),
  GQ("GQ", "Equatorial Guinea"),
  ER("ER", "Eritrea"),
  EE("EE", "Estonia"),
  ET("ET", "Ethiopia"),
  FK("FK", "Falkland Islands"),
  FO("FO", "Faroe Islands"),
  FJ("FJ", "Fiji"),
  FI("FI", "Finland"),
  FR("FR", "France"),
  GF("GF", "French Guiana"),
  PF("PF", "French Polynesia"),
  TF("TF", "French Southern Territories"),
  GA("GA", "Gabon"),
  GM("GM", "Gambia"),
  GE("GE", "Georgia"),
  DE("DE", "Germany"),
  GH("GH", "Ghana"),
  GI("GI", "Gibraltar"),
  GR("GR", "Greece"),
  GL("GL", "Greenland"),
  GD("GD", "Grenada"),
  GP("GP", "Guadeloupe"),
  GU("GU", "Guam"),
  GT("GT", "Guatemala"),
  GG("GG", "Guernsey"),
  GN("GN", "Guinea"),
  GW("GW", "Guinea_Bissau"),
  GY("GY", "Guyana"),
  HT("HT", "Haiti"),
  HM("HM", "Heard Island and Mcdonald Islands"),
  HN("HN", "Honduras"),
  HK("HK", "Hong_Kong"),
  HU("HU", "Hungary"),
  IS("IS", "Iceland"),
  IN("IN", "India"),
  ID("ID", "Indonesia"),
  IR("IR", "Iran"),
  IQ("IQ", "Iraq"),
  IE("IE", "Ireland"),
  IM("IM", "Isle Of Man"),
  IL("IL", "Israel"),
  IT("IT", "Italy"),
  JM("JM", "Jamaica"),
  JP("JP", "Japan"),
  JE("JE", "Jersey"),
  JO("JO", "Jordan"),
  KZ("KZ", "Kazakhstan"),
  KE("KE", "Kenya"),
  KI("KI", "Kiribati"),
  KP("KP", "North Korea"),
  KR("KR", "South Korea"),
  KW("KW", "Kuwait"),
  Kg("KG", "Kyrgyzstan"),
  LA("LA", "Lao"),
  LV("LV", "Latvia"),
  LB("LB", "Lebanon"),
  LS("LS", "Lesotho"),
  LR("LR", "Liberia"),
  LY("LY", "Libyan Arab Jamahiriya"),
  LI("LI", "Liechtenstein"),
  LT("LT", "Lithuania"),
  LU("LU", "Luxembourg"),
  MO("MO", "Macao"),
  MK("MK", "Macedonia"),
  MG("MG", "Madagascar"),
  MW("MW", "Malawi"),
  MY("MY", "Malaysia"),
  MV("MV", "Maldives"),
  ML("ML", "Mali"),
  MT("MT", "Malta"),
  MH("MH", "Marshall Islands"),
  MQ("MQ", "Martinique"),
  MR("MR", "Mauritania"),
  MU("MU", "Mauritius"),
  YT("YT", "Mayotte"),
  MX("MX", "Mexico"),
  FM("FM", "Micronesia"),
  MD("MD", "Moldova"),
  MC("MC", "Monaco"),
  MN("MN", "Mongolia"),
  ME("ME", "Montenegro"),
  MS("MS", "Montserrat"),
  MA("MA", "Morocco"),
  MZ("MZ", "Mozambique"),
  MM("MM", "Myanmar"),
  NA("NA", "Namibia"),
  NR("NR", "Nauru"),
  NP("NP", "Nepal"),
  NL("NL", "Netherlands"),
  AN("AN", "Netherlands Antilles"),
  NC("NC", "New Caledonia"),
  NZ("NZ", "New Zealand"),
  NI("NI", "Nicaragua"),
  NE("NE", "Niger"),
  NG("NG", "Nigeria"),
  NU("NU", "Niue"),
  NF("NF", "Norfolk Island"),
  MP("MP", "Northern Mariana Islands"),
  NO("NO", "Norway"),
  OM("OM", "Oman"),
  PK("PK", "Pakistan"),
  PW("PW", "Palau"),
  PS("PS", "Palestinian Territory Occupied"),
  PA("PA", "Panama"),
  PG("PG", "Papua New Guinea"),
  PY("PY", "Paraguay"),
  PE("PE", "Peru"),
  PH("PH", "Philippines"),
  PN("PN", "Pitcairn"),
  PL("PL", "Poland"),
  PT("PT", "Portugal"),
  PR("PR", "Puerto Rico"),
  QA("QA", "Qatar"),
  RE("RE", "Union"),
  RO("RO", "Romania"),
  RU("RU", "Russia"),
  RW("RW", "Rwanda"),
  BL("BL", "Lemy"),
  SH("SH", "St Helena"),
  KN("KN", "St Kitts and Nevis"),
  LC("LC", "St Lucia"),
  MF("MF", "St Martin"),
  PM("PM", "St Pierre and Miquelon"),
  VC("VC", "St Vincent and the Grenadines"),
  WS("WS", "Samoa"),
  SM("SM", "San_Marino"),
  ST("ST", "Sao Tome and Principe"),
  SA("SA", "Saudi Arabia"),
  SN("SN", "Senegal"),
  RS("RS", "Serbia"),
  SC("SC", "Seychelles"),
  SL("SL", "Sierra Leone"),
  SG("SG", "Singapore"),
  SK("SK", "Slovakia"),
  SI("SI", "Slovenia"),
  SB("SB", "Solomon Islands"),
  SO("SO", "Somalia"),
  ZA("ZA", "South Africa"),
  GS("GS", "South Georgia and the South Sandwich Islands"),
  ES("ES", "Spain"),
  LK("LK", "Sri Lanka"),
  SD("SD", "Sudan"),
  SR("SR", "Suriname"),
  SJ("SJ", "Svalbard and Jan Mayen"),
  SZ("SZ", "Swaziland"),
  SE("SE", "Sweden"),
  CH("CH", "Switzerland"),
  SY("SY", "Syria"),
  TW("TW", "Taiwan"),
  TJ("TJ", "Tajikistan"),
  TZ("TZ", "Tanzania"),
  TH("TH", "Thailand"),
  TL("TL", "Timor Leste"),
  TG("TG", "Togo"),
  TK("TK", "Tokelau"),
  TO("TO", "Tonga"),
  TT("TT", "Trinidad and Tobago"),
  TN("TN", "Tunisia"),
  TR("TR", "Turkey"),
  TM("TM", "Turkmenistan"),
  TC("TC", "Turks and Caicos Islands"),
  TV("TV", "Tuvalu"),
  UG("UG", "Uganda"),
  UA("UA", "Ukraine"),
  AE("AE", "United Arab Emirates"),
  GB("GB", "United Kingdom"),
  US("US", "United States"),
  UM("UM", "United States Minor Outlying Islands"),
  UY("UY", "Uruguay"),
  UZ("UZ", "Uzbekistan"),
  VU("VU", "Vanuatu"),
  VA("VA", "Vatican City"),
  VE("VE", "Venezuela"),
  VN("VN", "Vietnam"),
  VG("VG", "British Virgin Islands"),
  VI("VI", "US Virgin Islands"),
  WF("WF", "Wallis and Futuna"),
  EH("EH", "Western Sahara"),
  YE("YE", "Yemen"),
  ZM("ZM", "Zambia"),
  ZW("ZW", "Zimbabwe");

  private static final long                 serialVersionUID = -2671765295596548149L;
  private static final Map<String, Country> countryByCodeMap = new HashMap<>();
  private final String                      code;
  private final String                      description;

  static
  {
    for (Country country : Country.values())
    {
      countryByCodeMap.put(country.code, country);
    }
  }

  Country(String code, String description)
  {
    this.code        = code;
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

  /** @return  enum with this two letter code. If code is null, returns null */
  public static Country get(String code)
  {
    Country result = null;

    if (code != null)
    {
      result = countryByCodeMap.get(code.toUpperCase());
    }

    return result;
  }

  /** @return  enums with these two letter code */
  public static Country[] get(String... codes)
  {
    Country[] countries = new Country[codes.length];
    int       pos       = 0;

    for (String code : codes)
    {
      countries[pos++] = countryByCodeMap.get(code.toUpperCase());
    }

    return countries;
  }
}
