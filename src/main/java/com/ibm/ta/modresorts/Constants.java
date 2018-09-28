/* Copyright IBM Corp. 2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.ta.modresorts;

class Constants {

	public static final String BARCELONA = "Barcelona";
	public static final String CORK = "Cork";
	public static final String MIAMI = "Miami";
	public static final String SAN_FRANCISCO = "San_Francisco";
	public final static String PARIS = "Paris";
	public static final String LAS_VEGAS = "Las_Vegas";
	
	public final static String[] SUPPORTED_CITIES = {PARIS, LAS_VEGAS, SAN_FRANCISCO, MIAMI, CORK, BARCELONA};
	
	public final static String BACELONA_WEATHER_FILE = "barcelona20180810Weather.json";
	public final static String CORK_WEATHER_FILE = "cork20180810Weather.json";
	public final static String LAS_VEGAS_WEATHER_FILE = "lasVegas20180810Weather.json";
	public final static String MIAMI_WEATHER_FILE = "miami20180810Weather.json";
	public final static String PARIS_WEATHER_FILE = "paris20180810Weather.json";
	public final static String SAN_FRANCESCO_WEATHER_FILE = "sanFrancesco20180810Weather.json";
	
	// constants used to construct Weather Underground API
	public final static String WUNDERGROUND_API_PREFIX = "http://api.wunderground.com/api/";
	public final static String WUNDERGROUND_API_PART = "/forecast/geolookup/conditions/q/";
	

}
