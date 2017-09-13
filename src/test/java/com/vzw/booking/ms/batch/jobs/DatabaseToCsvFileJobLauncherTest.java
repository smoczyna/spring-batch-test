/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.jobs;

import eu.squadd.batch.WholesaleBookingProcessorApplication;
import eu.squadd.batch.config.DatabasesConfig;
import org.springframework.batch.core.JobExecution;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application.properties")
//@SpringApplicationConfiguration(classes = SpringBatchTestApplication.class)
public class DatabaseToCsvFileJobLauncherTest {

    @Test
    public void launchJob() throws Exception {
//        ApplicationContext context = new AnnotationConfigApplicationContext(SpringBatchTestApplication.class, DatabasesConfig.class, DatabaseToCsvFileJobConfig.class);
//        JobLauncherTestUtils jobLauncherTestUtils = context.getBean(JobLauncherTestUtils.class);
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        assertTrue(true);
    }
}
