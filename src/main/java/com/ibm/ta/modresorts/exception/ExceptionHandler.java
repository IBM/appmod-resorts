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

