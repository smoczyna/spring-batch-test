package com.vzw.booking.ms.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.vzw.booking.ms.template.config.LoggerConfig;
import com.vzw.vlf.lib.logger.VlfLogger;
import com.vzw.vlf.lib.logger.VlfLogger.LogLevel;
/**
 * <h1>TemplateApplicationInit</h1> TemplateApplicationInit is an initialization class. 
 * 1. Inject the application config class.
 * 2. Define a bean to initialize the logger based on the application specific configurations
 * Additional initializations can be autowired here.
 * <p>
 * 
 */
@Component
public class TemplateApplicationInit {
	
	@Autowired
	private LoggerConfig appConfig;
	
	/**
	 * Define a Bean to initialize the logger.
	 */
	@Bean 
	public VlfLogger vlfLogger() {
		VlfLogger logger =  new VlfLogger();
		logger.setAppName(appConfig.getAppname());
		logger.setServiceName(appConfig.getServicename());
		logger.setRegion(appConfig.getRegion());
		logger.setZone(appConfig.getZone());
		VlfLogger.setLogLevel(LogLevel.INFO);
		return logger;
		}

}
