/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.out;

import com.vzw.booking.ms.batch.SpringBatchTestApplication;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = SpringBatchTestApplication.class)
public class DatabaseToCsvFileJobLauncherTest {
    
    public DatabaseToCsvFileJobLauncherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void launchJob() throws Exception {

//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);

    
    }
    
}
