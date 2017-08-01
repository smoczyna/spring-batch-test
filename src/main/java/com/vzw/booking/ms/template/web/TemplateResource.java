package com.vzw.booking.ms.template.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.vzw.booking.ms.template.api.TemplateRequest;

/**
 * <h1>TemplateResource</h1> TemplateResource defines the resource interface for the application.
 * All methods under the resource are defined here.
 * <p>
 */
public interface TemplateResource {
	
	ResponseEntity<?> runQuery(@RequestBody TemplateRequest sampleRequest);

}
