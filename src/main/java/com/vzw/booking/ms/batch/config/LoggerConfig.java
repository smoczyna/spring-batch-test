package com.vzw.booking.ms.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <h1>LoggerConfig</h1> LoggerConfig provides access to the values in
 * the application.properties file. This example retrieves the logger
 * initialization values from the application.properties file.
 * <p>
 * 
 */
@Component
public class LoggerConfig {
	
	@Value("${vlf.logger.appname}")
	private String appname;
	@Value("${vlf.logger.servicename}")
	private String servicename;
	@Value("${vlf.logger.region}")
	private String region;
	@Value("${vlf.logger.zone}")
	private String zone;
	/**
	 * @param appname
	 * @param servicename
	 * @param region
	 * @param zone
	 */
	public LoggerConfig(String appname, String servicename, String region, String zone) {
		this.appname = appname;
		this.servicename = servicename;
		this.region = region;
		this.zone = zone;
	}
	
	public LoggerConfig() {
	}

	/**
	 * @return the appname
	 */
	public String getAppname() {
		return appname;
	}

	/**
	 * @param appname the appname to set
	 */
	public void setAppname(String appname) {
		this.appname = appname;
	}

	/**
	 * @return the servicename
	 */
	public String getServicename() {
		return servicename;
	}

	/**
	 * @param servicename the servicename to set
	 */
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
	

}
