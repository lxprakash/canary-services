/**
 * 
 */
package com.intigna.services.canary.rest.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datacode2.microservices.commons.CommonServiceUtils;
import com.datacode2.microservices.commons.rest.data.RequestDetails;
import com.datacode2.microservices.commons.rest.data.logging.LoggingGETData;
import com.intigna.services.canary.rest.api.ILoggingServices;


/**
 * Defines the implementation of the Commission REST services.
 * @author adevoe
 * 
 */
public class LoggingServices 
implements ILoggingServices 
{
	private final static Logger logger = LoggerFactory.getLogger(LoggingServices.class);

	@Override
	public LoggingGETData logInfo(String level, String category, String message, String exceptionMessage) throws Exception {
		Logger l = LoggerFactory.getLogger(category);
		Exception e = null;
		if (exceptionMessage != null && exceptionMessage.length() > 0) {
			e = new Exception(exceptionMessage);
		}
		if (level == null || level.equalsIgnoreCase("info")) {
			if (e == null ) {
				l.info(message) ;
			} else {
				l.info(message, e);
			}
		} else if (level.equalsIgnoreCase("error")) {
			if (e == null ) {
				l.error(message) ;
			} else {
				l.error(message, e);
			}
		} else if (level.equalsIgnoreCase("debug")) {
			if (e == null ) {
				l.debug(message) ;
			} else {
				l.debug(message, e);
			}
		} else {
			throw new Exception("LoggingService log level " + level + " is bad.");
		}
	
		LoggingGETData m = new LoggingGETData();
		m.setCategory(category);
		m.setLevel(level);
		m.setMessage(message);
		m.setExceptionMessage(exceptionMessage);
		return m;
	}

	@Override
	public LoggingGETData addExtraInfo(String category, String message, String countStr) throws Exception {
		if (countStr == null || countStr.length() == 0) {
			countStr = "1";
		}
		Integer count = new Integer(countStr);
		for (int i = 0; i < count.intValue(); i++) {
			RequestDetails.extraInfo(category, i + ". " + message );
		}
		LoggingGETData gd = new LoggingGETData();
		return gd;
	}

	
	 
}