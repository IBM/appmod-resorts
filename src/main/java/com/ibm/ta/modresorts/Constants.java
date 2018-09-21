package com.ibm.ta.modresorts;

class Constants {

  public static final String BARCELONA = "Barcelona";
  public static final String CORK = "Cork";
  public static final String MIAMI = "Miami";
  public static final String SAN_FRANCISCO = "San_Francisco";
  public static final String PARIS = "Paris";
  public static final String LAS_VEGAS = "Las_Vegas";

  public static final String[] SUPPORTED_CITIES = {
    PARIS, LAS_VEGAS, SAN_FRANCISCO, MIAMI, CORK, BARCELONA
  };

  public static final String BACELONA_WEATHER_FILE = "barcelona20180810Weather.json";
  public static final String CORK_WEATHER_FILE = "cork20180810Weather.json";
  public static final String LAS_VEGAS_WEATHER_FILE = "lasVegas20180810Weather.json";
  public static final String MIAMI_WEATHER_FILE = "miami20180810Weather.json";
  public static final String PARIS_WEATHER_FILE = "paris20180810Weather.json";
  public static final String SAN_FRANCESCO_WEATHER_FILE = "sanFrancesco20180810Weather.json";

  // constants used to construct Weather Underground API
  public static final String WUNDERGROUND_API_PREFIX = "http://api.wunderground.com/api/";
  public static final String WUNDERGROUND_API_PART = "/forecast/geolookup/conditions/q/";
}
