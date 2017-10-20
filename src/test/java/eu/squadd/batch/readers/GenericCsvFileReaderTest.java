/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author smoczyna
 * @param <T>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration
public abstract class GenericCsvFileReaderTest<T extends Object> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericCsvFileReaderTest.class);
    public abstract CsvFileGenericReader getReader();
    public abstract File getFile();
    protected int numberOfRecords;
    
    @Test
    public void testReader() {
        File file = this.getFile();
        if (!file.exists())
            fail("Source file missing !!!");
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                this.getReader().open(execution.getExecutionContext());
                T inputRecord;
                int readCount = 0;
                try {
                    while ((inputRecord = (T) this.getReader().read()) != null) {
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
        assertEquals(numberOfRecords, count);
    }
}
