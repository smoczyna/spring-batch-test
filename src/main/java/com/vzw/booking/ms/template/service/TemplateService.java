package com.vzw.booking.ms.template.service;

import com.vzw.booking.ms.template.api.TemplateRequest;
import com.vzw.booking.ms.template.api.TemplateResponse;

/**
 * <h1>TemplateService</h1> TemplateService defines the service interfaces to be implemented. 
 * In this example, the service only has one method, which runs a query.
 * <p>
 */
public interface TemplateService {
	
	TemplateResponse runQuery(TemplateRequest sampleRequest) ;

}
