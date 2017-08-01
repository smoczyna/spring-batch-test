package com.vzw.booking.ms.template.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vzw.booking.ms.template.api.TemplateResponse;
import com.vzw.booking.ms.template.api.TemplateResponse.ResponseStatus;
import com.vzw.booking.ms.template.constants.Constants;
import com.vzw.vlf.lib.logger.VlfLogger;
import com.vzw.vlf.lib.logger.VlfLogger.Category;
import com.vzw.vlf.lib.logger.VlfLogger.LogLevel;
import com.vzw.vlf.lib.logger.VlfLogger.Severity;
import com.vzw.vlf.lib.logger.VlfLogger.Status;

/**
 * <h1>GlobalExceptionHandler</h1> The GlobalExceptionHandler class provides consistent exception
 * handling across the application. It eliminates the need to code exception handling
 * throughout the code. Rather, exceptions will be mapped to the appropriate routine based
 * on the exception type. 
 * <p>
 * This example performs 2 steps.
 * 1. The exception is logged. Note that the logs contain the full exception details.
 * 2. Create and send a response object. The response object is populated with a high level
 * description of the issue only. Exception details should not be returned to the caller so
 * that internals are not exposed, potentiall creating a security risk.
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private VlfLogger logger;

	/**
	 * Exception handler for IllegalArgumentException and IllegalStateException exceptions.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		TemplateResponse sampleResponse = new TemplateResponse(ResponseStatus.FAILED, ex.getMessage(), null);
		this.logger.write("boom", Severity.ERROR, ex);
		this.logger.write("boom", Constants.RESPONSE, Severity.INFO, sampleResponse, LogLevel.ERROR);
		this.logger.endLogSet(Status.ERROR, Category.SYSTEM_ERROR, ex.getClass().getName(), ex.getMessage());
		return handleExceptionInternal(ex, sampleResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	/**
	 * Exception handler for untrapped exceptions.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> unhandled(RuntimeException ex, WebRequest request) {
		TemplateResponse sampleResponse = new TemplateResponse(ResponseStatus.FAILED, ex.getMessage(), null);
		this.logger.write("boom", Severity.ERROR, ex);
		this.logger.write("boom", Constants.RESPONSE, Severity.INFO, sampleResponse, LogLevel.ERROR);
		this.logger.endLogSet(Status.ERROR, Category.SYSTEM_ERROR, ex.getClass().getName(), ex.getMessage());
		return handleExceptionInternal(ex, sampleResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
