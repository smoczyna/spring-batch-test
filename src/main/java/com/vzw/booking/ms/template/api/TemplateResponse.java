/**
 * 
 */
package com.vzw.booking.ms.template.api;

import java.util.List;

import com.vzw.booking.ms.template.constants.Constants;
import com.vzw.booking.ms.template.domain.Alljobdata;

/**
 * <h1>TemplateResponse</h1> TemplateResponse defines the service response. This object 
 * is converted to it's JSON string and returned to the calling application.
 * <p>
 * 
 */
public class TemplateResponse {
	
	public enum ResponseStatus {
		OK,
		WARNING,
		FAILED,
		VALIDATION_FAILED
	}
	
	/**
	 * Defines the fields in the JSON document.
	 */
	private ResponseStatus responseStatus;
	private String message = Constants.EMPTY_STRING;
	private List<Alljobdata> jobData;
	/**
	 * @param responseStatus
	 * @param message
	 * @param jobData
	 */
	public TemplateResponse(ResponseStatus responseStatus, String message, List<Alljobdata> jobData) {
		this.responseStatus = responseStatus;
		this.message = message;
		this.jobData = jobData;
	}
	
	public TemplateResponse() {
	}

	/**
	 * @return the responseStatus
	 */
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the jobData
	 */
	public List<Alljobdata> getJobData() {
		return jobData;
	}

	/**
	 * @param jobData the jobData to set
	 */
	public void setJobData(List<Alljobdata> jobData) {
		this.jobData = jobData;
	}

}
