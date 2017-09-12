package com.intigna.services.canary.messages;

import java.util.Locale;

import com.datacode2.microservices.commons.constants.Severity;
import com.intigna.services.canary.spring.CanaryServicesBeanFactory;

public enum ErrorCodeAndMessage
{
	//*** Need to be removed, while adding actual Error Messages and Codes. ***/
	ERROR_MESSAGE(Severity.ERROR, CanaryServicesBeanFactory.getMessage("ERROR_MESSAGE", null, Locale.US));
	
	private Severity severityLevel;
	private String errorMessage;
	
	/**
	 * @param severityLevel
	 * @param errorMessage
	 */
	private ErrorCodeAndMessage(Severity severityLevel, String errorMessage)
	{
		this.severityLevel = severityLevel;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the severityLevel
	 */
	public Severity getSeverityLevel()
	{
		return severityLevel;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}
}