package com.vzw.booking.ms.template.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vzw.booking.ms.template.api.TemplateRequest;
import com.vzw.booking.ms.template.api.TemplateResponse;
import com.vzw.booking.ms.template.api.TemplateResponse.ResponseStatus;
import com.vzw.booking.ms.template.constants.Constants;
import com.vzw.booking.ms.template.service.TemplateServiceImpl;
import com.vzw.vlf.lib.logger.VlfLogger;
import com.vzw.vlf.lib.logger.VlfLogger.Category;
import com.vzw.vlf.lib.logger.VlfLogger.LogLevel;
import com.vzw.vlf.lib.logger.VlfLogger.Severity;
import com.vzw.vlf.lib.logger.VlfLogger.Status;

/**
 * <h1>TemplateResourceImpl</h1> TemplateResourceImpl creates the REST controller for the service.
 * It defines the web entry points and maps them to the appropriate methods.
 * <p>
 */
@RestController
@RequestMapping("/")
public class TemplateResourceImpl implements TemplateResource {
		
	/**
	 * Inject the logger. 
	 */
	private VlfLogger logger;
	@Autowired
	public void setLogger(VlfLogger logger) {
		this.logger = logger;
	}

	/**
	 * Inject the service implementation. 
	 */
	@Autowired
	private TemplateServiceImpl sampleServiceImpl;
	
	/**
	 * runQuery is the main method for the service. It performs the following functions:
	 * <p>1. Create a new log set for the current transaction.
	 * <br>2. Log the input request.
	 * <br>3. Call the service implementation.
	 * <br>4. Create the response entity.
	 * <br>5. Log the response.
	 * <br>6. End the log set.
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
			"application/json" })
	public ResponseEntity<?> runQuery(@RequestBody TemplateRequest sampleRequest) {
		Status status = Status.SUCCESS;
		Category category = Category.SUCCESS;
		this.logger.newLogSet("query");
		this.logger.write("query", Constants.REQUEST, Severity.INFO, sampleRequest, LogLevel.ERROR);

		TemplateResponse response = sampleServiceImpl.runQuery(sampleRequest);
		
		ResponseEntity<?>  resentity = ResponseEntity.status(response.getResponseStatus() == ResponseStatus.VALIDATION_FAILED?HttpStatus.BAD_REQUEST:HttpStatus.OK).body(response);
		if(response.getResponseStatus() == ResponseStatus.VALIDATION_FAILED) {
			status = Status.ERROR;
			category = Category.INPUT_DATA_ERROR;
		}
		else if(response.getResponseStatus() == ResponseStatus.WARNING) {
			status = Status.WARNING;
		}
		this.logger.write("query", Constants.RESPONSE, Severity.INFO, resentity.getBody(), LogLevel.ERROR);
		this.logger.endLogSet(status, category,	response.getResponseStatus().toString(), response.getMessage());

		return resentity;

	}

}
