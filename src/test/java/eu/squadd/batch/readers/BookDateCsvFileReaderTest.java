/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({StepScopeTestExecutionListener.class})
@ContextConfiguration
//@TestPropertySource(properties = {"source.files.path", "/home/smoczyna/NetBeansProjects/spring-batch-test/src/main/resources/data"})
public class BookDateCsvFileReaderTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookDateCsvFileReaderTest.class);
    private BookDateCsvFileReader reader;
   
    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./data/bookdate.csv").getFile());
        if (file.exists())
            reader = new BookDateCsvFileReader(file.getAbsolutePath());
        else
            fail("Source file missing !!!");
        
        //reader = new BookDateCsvFileReader("/home/smoczyna/NetBeansProjects/spring-batch-test/src/main/resources/data/bookdate.csv");        
    }

    @Test
    public void testReader() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(execution, () -> {
                reader.open(execution.getExecutionContext());
                BookDateCsvFileDTO inputRecord;
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
                } finally {
                    try { reader.close(); } catch (ItemStreamException e) { fail(e.toString());
                    }
                }
                return readCount;                
            });
        } catch (Exception e) {
            fail(e.toString());
        }
        //assertEquals(1, count);
    }
}
