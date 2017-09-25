/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author smoczyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class})
@ContextConfiguration
public class WholesaleReportCsvWriterTest {

    private WholesaleReportCsvWriter writer;

    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("./data");
        System.out.println("write path: "+url.getPath());
        writer = new WholesaleReportCsvWriter(url.getPath()+"/wholesale_report.csv");
        //writer = new WholesaleReportCsvWriter("/home/smoczyna/NetBeansProjects/spring-batch-test/src/main/resources/data/wholesale_report.csv");
    }

    @Test
    public void testReader() throws Exception {
        List<AggregateWholesaleReportDTO> report = new LinkedList();
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("ind1");
        record.setHomeFinancialMarketId("Dublin");
        // populate something 
        report.add(record);

        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        StepScopeTestUtils.doInStepScope(execution, () -> {
            try {
                writer.open(execution.getExecutionContext());
                writer.write(report);
                writer.close();
                // verify the file was saved and its contents
                
            } catch (Exception ex) {
                Logger.getLogger(WholesaleReportCsvWriterTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 1;
        });
    }

}
