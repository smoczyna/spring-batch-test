package com.vzw.booking.ms.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vzw.booking.ms.template.api.TemplateRequest;
import com.vzw.booking.ms.template.api.TemplateResponse;
import com.vzw.booking.ms.template.api.TemplateResponse.ResponseStatus;
import com.vzw.booking.ms.template.domain.Alljobdata;
import com.vzw.booking.ms.template.repository.AllJobDataRepository;
import com.vzw.booking.ms.template.validation.RequestValidation;
import com.vzw.vlf.lib.logger.*;
import com.vzw.vlf.lib.logger.VlfLogger.Category;
import com.vzw.vlf.lib.logger.VlfLogger.LogLevel;
import com.vzw.vlf.lib.logger.VlfLogger.Status;

/**
 * <h1>TemplateServiceImpl</h1> TemplateServiceImpl is the implementation of the
 * TemplateService interface. This class adds the method to call the database
 * and retrieve the domain objects.
 * <p>
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	/**
	 * Inject the repository to access the database.	
	 */
	@Autowired
	private AllJobDataRepository allJobDataRepository;
	
	/**
	 * Inject the validator.	
	 */
	@Autowired
	private RequestValidation requestValidation;
	
	/**
	 * Inject the logger. 
	 */
	private VlfLogger logger;
	@Autowired
	public void setLogger(VlfLogger logger) {
		this.logger = logger;
	}

	/**
	 * runQuery is the implementation of the query method. It performs the following functions:
	 * <p>1. Validate the input. Fail if invalid.
	 * <br>2. Call the appropriate method from the repository based on the requested operation.
	 * <br>3. Create the response object with the query results.
	 * 
	 */
	@Override
	public TemplateResponse runQuery(TemplateRequest sampleRequest) {
		String eventId;
		TemplateResponse response;
		List<Alljobdata> jobData = null;
		if(!requestValidation.validate(sampleRequest)) {  /* validation failed. Get out. */
			return new TemplateResponse(ResponseStatus.VALIDATION_FAILED, "Validation Failed", null);
		}

		switch (sampleRequest.getOperation()) {

		case byJobName:
			eventId = this.logger.newEvent("AllJobDataRepository", "TemplateApplication", "findByJobname", "SQL", 1, LogLevel.INFO);
			jobData = allJobDataRepository.findByJobname(sampleRequest.getOperand());
			this.logger.endEvent(eventId, jobData.size()> 0?"0":"4",jobData.size(), 
					jobData.size()> 0?Status.SUCCESS:Status.WARNING,
					Category.SUCCESS, "OK");
			break;

		case byJobNameLike:
			eventId = this.logger.newEvent("AllJobDataRepository", "TemplateApplication", "byJobNameLike", "SQL", 1, LogLevel.INFO);
			jobData = allJobDataRepository.findByJobnameLike(sampleRequest.getOperand());
			this.logger.endEvent(eventId, jobData.size()> 0?"0":"4",jobData.size(), 
					jobData.size()> 0?Status.SUCCESS:Status.WARNING,
					Category.SUCCESS, "OK");
			break;

		case byJobNameUsingSP:
			eventId = this.logger.newEvent("AllJobDataRepository", "TemplateApplication", "byJobNameLike", "SQL", 1, LogLevel.INFO);
			jobData = allJobDataRepository.findByJobnameSP(sampleRequest.getOperand());
			this.logger.endEvent(eventId, jobData.size()> 0?"0":"4",jobData.size(), 
					jobData.size()> 0?Status.SUCCESS:Status.WARNING,
					Category.SUCCESS, "OK");
			break;

		default:
			break;
		}

		response = new TemplateResponse();
		if (jobData != null) {
			response.setResponseStatus(ResponseStatus.OK);
			response.setJobData(jobData);
		} else {
			response.setResponseStatus(ResponseStatus.WARNING);
			response.setMessage("No data returned from query");
			response.setJobData(null);
		}

		return response;
	}
}
