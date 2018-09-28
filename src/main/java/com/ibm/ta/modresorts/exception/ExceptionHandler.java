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

package com.ibm.ta.modresorts.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

public class ExceptionHandler {
	
	public static void handleException(Exception e, String errorMsg, Logger logger) throws ServletException {
		if (e == null) {
			logger.severe(errorMsg);
			throw new ServletException(errorMsg);
		}else {
			logger.log(Level.SEVERE, errorMsg, e);
			throw new ServletException(errorMsg, e);
		}
	}
}

