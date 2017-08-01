/**
 * 
 */
package com.vzw.booking.ms.template.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <h1>Alljobdata</h1> Alljobdata is an example of a domain class. It defines the alljobdata table
 * in a MySQL database. Classes in the domain package map directly to business entities 
 * such as SQL tables, JSON documents, etc.
 * <p>
 * 
 */
@Entity
public class Alljobdata {
	/* These annotations identify 'id' and the primary keys and as
	 * an auto-increment field in MySQL.
	 */
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String jobname;
	private String schedulestartdatetime;
	private String firststart;
	private String application;
	private String displaystatus;
	private String category;
	
	/**
	 * @param jobName
	 * @param scheduleStartDate
	 * @param firstStart
	 * @param application
	 * @param displayStatus
	 * @param category
	 * @param id
	 */
	public Alljobdata(String jobName, String scheduleStartDate, String firstStart, String application, String displayStatus,
			String category, long id) {
		this.jobname = jobName;
		this.schedulestartdatetime = scheduleStartDate;
		this.firststart = firstStart;
		this.application = application;
		this.displaystatus = displayStatus;
		this.category = category;
		this.id = id;
	}

	public Alljobdata() {
	}
	
	public String toString() {
		return String.format(
                "AllJobData[id=%d, jobName='%s', scheduleStartDate=%s, firstStart=%s, application='%s', displayStatus='%s', category='%s']",
                id, jobname, schedulestartdatetime, firststart, application, displaystatus, category);
	}

	/**
	 * @return the jobName
	 */
	public String getJobname() {
		return jobname;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobname(String jobName) {
		this.jobname = jobName;
	}

	/**
	 * @return the scheduleStartDate
	 */
	public String getSchedulestartdatetime() {
		return schedulestartdatetime;
	}

	/**
	 * @param scheduleStartDate the scheduleStartDate to set
	 */
	public void setSchedulestartdatetime(String scheduleStartDate) {
		this.schedulestartdatetime = scheduleStartDate;
	}

	/**
	 * @return the firstStart
	 */
	public String getFirststart() {
		return firststart;
	}

	/**
	 * @param firstStart the firstStart to set
	 */
	public void setFirststart(String firstStart) {
		this.firststart = firstStart;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the displayStatus
	 */
	public String getDisplaystatus() {
		return displaystatus;
	}

	/**
	 * @param displayStatus the displayStatus to set
	 */
	public void setDisplaystatus(String displayStatus) {
		this.displaystatus = displayStatus;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}
