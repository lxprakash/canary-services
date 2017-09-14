package com.intigna.services.canary.rest.data;

import com.datacode2.microservices.commons.rest.data.DateGETData;

public class LoggingGETData {
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public DateGETData getTheDateCreated() {
		return theDateCreated;
	}
	public void setTheDateCreated(DateGETData theDateCreated) {
		this.theDateCreated = theDateCreated;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String level;
	private String category;
	private String message;
	private String exceptionMessage = null;
	private DateGETData theDateCreated;
	
	
}
