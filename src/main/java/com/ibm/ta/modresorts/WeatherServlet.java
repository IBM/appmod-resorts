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

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ibm.ta.modresorts.exception.ExceptionHandler;

public class WeatherServlet extends HttpServlet {


	// local OS environment variable key name.  The key value should provide an API key that will be used to
	// get weather information from site: http://www.wunderground.com
	private static final String WEATHER_API_KEY = "WEATHER_API_KEY";  
	
	static final long serialVersionUID = 1L;
	  
	private static final Logger logger = Logger.getLogger(WeatherServlet.class.getName());

	/**
	 * constructor
	 */
	public WeatherServlet() {
		super();
	}

	/**
	 * Returns the weather information for a given city
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String methodName = "doGet";
		logger.entering(WeatherServlet.class.getName(), methodName);

		String city = request.getParameter("selectedCity");
		logger.log(Level.FINE, "requested city is " + city);
		
		String weatherAPIKey = System.getenv(WEATHER_API_KEY);
		String mockedKey = mockKey(weatherAPIKey);
		logger.log(Level.FINE, "weatherAPIKey is " + mockedKey);
		
		if (weatherAPIKey != null && weatherAPIKey.trim().length() > 0) {
			logger.info("weatherAPIKey is found, system will provide the real time weather data for the city " + city);
			getRealTimeWeatherData(city, weatherAPIKey, response);
		}else {
			logger.info("weatherAPIKey is not found, will provide the weather data dated August 10th, 2018 for the city " + city);
			getDefaultWeatherData(city, response);
		}
	}
	
	private void getRealTimeWeatherData(String city, String apiKey, HttpServletResponse response) 
			throws ServletException, IOException {
		String resturl = null;
		String resturlbase = Constants.WUNDERGROUND_API_PREFIX + apiKey + Constants.WUNDERGROUND_API_PART;

	    if (Constants.PARIS.equals(city)) {
	            resturl = resturlbase + "France/Paris.json";
	        } else if (Constants.LAS_VEGAS.equals(city)) {
	            resturl = resturlbase + "NV/Las_Vegas.json";
	        } else if (Constants.SAN_FRANCISCO.equals(city)) {
	            resturl = resturlbase + "/CA/San_Francisco.json";
	        } else if (Constants.MIAMI.equals(city)) {
	            resturl = resturlbase + "FL/Miami.json";
	        } else if (Constants.CORK.equals(city)) {
	            resturl = resturlbase + "ireland/cork.json";
	        } else if (Constants.BARCELONA.equals(city)) {
	            resturl = resturlbase + "Spain/Barcelona.json";
		}else {			
			String errorMsg = "Sorry, the weather information for your selected city: " + city + 
					" is not available.  Valid selections are: " + Arrays.toString(Constants.SUPPORTED_CITIES);
			ExceptionHandler.handleException(null, errorMsg, logger);
		}
			
		URL obj;
		HttpURLConnection con = null;
		try {
			obj = new URL(Objects.requireNonNull(resturl));
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
		} catch (MalformedURLException e1) {
			String errorMsg = "Caught MalformedURLException. Please make sure the url is correct.";
			ExceptionHandler.handleException(e1, errorMsg, logger);
		}catch (ProtocolException e2) {
			String errorMsg = "Caught ProtocolException: " + e2.getMessage() + ". Not able to set request method to http connection.";
			ExceptionHandler.handleException(e2, errorMsg, logger);
		} catch (IOException e3) {
			String errorMsg = "Caught IOException: " + e3.getMessage() + ". Not able to open connection.";
			ExceptionHandler.handleException(e3, errorMsg, logger);
		} 
		
		int responseCode = Objects.requireNonNull(con).getResponseCode();
		logger.log(Level.FINEST, "Response Code: " + responseCode);		
		
		if (responseCode >= 200 && responseCode < 300) {

			BufferedReader in = null;
			ServletOutputStream out = null;

			try {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder responseStr = new StringBuilder();
				
				while ((inputLine = in.readLine()) != null) {
					responseStr.append(inputLine);
				}
	
				response.setContentType("application/json");
				out = response.getOutputStream();
				out.print(responseStr.toString());
				logger.log(Level.FINE, "responseStr: " + responseStr);
			} catch (Exception e) {
				String errorMsg = "Problem occured when processing the weather server response.";
				ExceptionHandler.handleException(e, errorMsg, logger);
			} finally {
				if (in != null) {
					in.close();
				}
				if (out!= null) {
					out.close();
				}
            }
		} else {
			String errorMsg = "REST API call " + resturl + " returns an error response: " + responseCode;
			ExceptionHandler.handleException(null, errorMsg, logger);
		}
	}
	
	private void getDefaultWeatherData(String city, HttpServletResponse response) 
			throws ServletException, IOException {
		DefaultWeatherData defaultWeatherData = null;
	
		try {
			defaultWeatherData = new DefaultWeatherData(city);
		}catch (UnsupportedOperationException e) {
			ExceptionHandler.handleException(e, e.getMessage(), logger);
		}
		
		ServletOutputStream out = null;
		
		try {
			String responseStr = Objects.requireNonNull(defaultWeatherData).getDefaultWeatherData();
			response.setContentType("application/json");
			out = response.getOutputStream();
			out.print(responseStr);
			logger.log(Level.FINEST, "responseStr: " + responseStr);
		} catch (Exception e) {
				String errorMsg = "Problem occured when getting the default weather data.";
				ExceptionHandler.handleException(e, errorMsg, logger);
		} finally {
				
			if (out!= null) {
				out.close();
			}

        }
	}

	/**
	 * Returns the weather information for a given city
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}	
	
	private static String mockKey(String toBeMocked) {
		if (toBeMocked == null) {
			return null;
		}
		String lastToKeep = toBeMocked.substring(toBeMocked.length()-3);
		return "*********" + lastToKeep;
	}
	
}
