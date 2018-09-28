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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class DefaultWeatherData {
	
	private final static Logger logger = Logger.getLogger(DefaultWeatherData.class.getName());
	
	private String city;
	
	private String getCity() {
		return city;
	}

	public DefaultWeatherData(String city) {
		if (city == null) {
			logger.severe("fail initializing DefaultWeatherData because the given city value is null");
			throw new UnsupportedOperationException("City is not defined");
		}
		boolean isSupportedCity = false;
		
		for (String aSupportedCity : Constants.SUPPORTED_CITIES) {
			if (city.equals(aSupportedCity)) {
				isSupportedCity = true;
			}
		}
		if (isSupportedCity) {
			this.city = city;
		} else {
			logger.severe("fail initializing DefaultWeatherData because the given city " + city + " is not supported");
			throw new UnsupportedOperationException("City is invalid. It must be one of " + Arrays.toString(Constants.SUPPORTED_CITIES));
		}
	}
	
	public String getDefaultWeatherData () throws IOException {		
		
		String dataFileName;
		if (Constants.PARIS.equals(getCity())) {
			dataFileName = Constants.PARIS_WEATHER_FILE;
		} else if (Constants.LAS_VEGAS.equals(getCity())) {
			dataFileName = Constants.LAS_VEGAS_WEATHER_FILE;
		} else if (Constants.SAN_FRANCISCO.equals(getCity())) {
			dataFileName = Constants.SAN_FRANCESCO_WEATHER_FILE;
		} else if (Constants.MIAMI.equals(getCity())) {
			dataFileName = Constants.MIAMI_WEATHER_FILE;
		} else if (Constants.CORK.equals(getCity())) {
			dataFileName = Constants.CORK_WEATHER_FILE;
		} else if (Constants.BARCELONA.equals(getCity())) {
			dataFileName = Constants.BACELONA_WEATHER_FILE;
		}else {
			throw new UnsupportedOperationException("The default weather information for the selected city: " + city + 
					" is not provided.  Valid selections are: " + Arrays.toString(Constants.SUPPORTED_CITIES));
		}
		logger.log(Level.FINE, "dataFileName: " + dataFileName);
		
		InputStream inputStream = null;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
			byte[] buf = new byte[4096];
			for (int n; 0 < (n = inputStream.read(buf));) {
				out.write(buf, 0, n);
			}
		} finally {
			out.close();
			
			if (inputStream != null) {
				inputStream.close();
			}
        }
				
	    String resultStr = new String(out.toByteArray(), StandardCharsets.UTF_8);
	    logger.log(Level.FINEST, "resultStr: " + resultStr);
        return resultStr;
		
	}

}
