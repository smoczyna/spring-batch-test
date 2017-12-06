/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
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
public class SubledgerFixedLengthFielWriterTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SubledgerFixedLengthFielWriterTest.class);
    private SubledgerFixedLengthFileWriter writer;
    private String workingFoler;
    
    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        workingFoler = classLoader.getResource("./data").getPath();
        LOGGER.info("Write path: {0}", workingFoler);
        writer = new SubledgerFixedLengthFileWriter(workingFoler+"/fixed_length_subledger_report.txt");
    }
    
    @Test
    public void testWriter() throws Exception {
        List<SummarySubLedgerDTO> list = new LinkedList();
        SummarySubLedgerDTO item = new SummarySubLedgerDTO();
        item.setFinancialCategory(123);
        item.setFinancialEventNumber(456789);
        item.setFinancialmarketId("DUB");
        item.setSubledgerTotalCreditAmount(-1234.78);
        item.setSubledgerTotalDebitAmount(86654.89);
        item.setUpdateUserId("WSBTest1"); // default value is too long !!!
        list.add(item);
        
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(execution, () -> {
            try {
                writer.open(execution.getExecutionContext());
                writer.write(list);
                writer.close();
                verifyWrittenFile();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
            return 1;
        });
    }
    
    private void verifyWrittenFile() throws IOException, Exception {
        File file = new File(workingFoler+"/fixed_length_subledger_report.txt");
        assertNotNull(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        assertTrue(line.length()==170);
        // add above fields presence check 
    }
}
