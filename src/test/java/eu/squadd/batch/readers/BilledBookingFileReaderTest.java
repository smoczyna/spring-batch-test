/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration
public class BilledBookingFileReaderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BilledBookingFileReaderTest.class);
    private BilledBookingFileReader reader;
    private String os;
    private String delimiter;
    
    @Before
    public void setup() {
        this.os = System.getProperty("os.name");
        if (this.os.contains("Windows"))
            this.delimiter = "Â¦";
        else
            this.delimiter = "¦";            
    }
    
    @Test
    public void testReader() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./data/bmdunld.csv").getFile());
        if (file.exists())
            reader = new BilledBookingFileReader(file.getAbsolutePath(), this.delimiter);
        else
            fail("Source file missing !!!");
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                reader.open(execution.getExecutionContext());
                BilledCsvFileDTO inputRecord;
                int readCount = 0;
                try {
                    while ((inputRecord = reader.read()) != null) {
                        assertNotNull(inputRecord);
                        System.out.println("*** Input data ***");
                        System.out.println(inputRecord.toString());
                        System.out.println("*** End of data ***");
                        readCount++;
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                }
                return readCount;
            });
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        assertEquals(1, count);
    }
    
    @Test //(expected = FlatFileParseException.class)
    public void readerFailTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./data/bmdunld.csv").getFile());
        if (file.exists())
            reader = new BilledBookingFileReader(file.getAbsolutePath(), "|");
        else
            fail("Source file missing !!!");
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                reader.open(execution.getExecutionContext());
                BilledCsvFileDTO inputRecord;
                int readCount = 0;
                try {
                    while ((inputRecord = reader.read()) != null) {
                        assertNotNull(inputRecord);
                        System.out.println("*** Input data ***");
                        System.out.println(inputRecord.toString());
                        System.out.println("*** End of data ***");
                        readCount++;
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                }
                return readCount;
            });
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            assertTrue(ex instanceof FlatFileParseException);
        }
        assertEquals(0, count);
    }
}
