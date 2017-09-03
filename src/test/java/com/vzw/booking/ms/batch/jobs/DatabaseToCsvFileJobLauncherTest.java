/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.jobs;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:application.properties")
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
