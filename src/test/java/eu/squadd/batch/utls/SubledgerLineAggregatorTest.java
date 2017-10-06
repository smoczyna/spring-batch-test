/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.utls;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.utils.FixedLengthLineAggregator;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.StepScopeTestExecutionListener;
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
public class SubledgerLineAggregatorTest {
    private FixedLengthLineAggregator lineAggregator;
    
    @Before
    public void setUp() {
        Map<String, Integer> fields = new LinkedHashMap();
        fields.put("jemsApplId", 2);
        fields.put("reportStartDate", 10);
        fields.put("jemsApplTransactioDate", 10);
        fields.put("financialEventNumber", 10);
        fields.put("financialCategory", 10);
        fields.put("financialmarketId", 3);
        fields.put("subledgerSequenceNumber", 10);
        fields.put("subledgerTotalDebitAmount", 14);
        fields.put("subledgerTotalCreditAmount", 14);
        fields.put("jurnalEventNumber", 10);
        fields.put("jurnalEventExceptionCode", 4);
        fields.put("jurnalEventReadInd", 1);
        fields.put("generalLedgerTransactionNumber", 10);
        fields.put("billCycleNumber", 2);
        fields.put("billTypeCode", 2);
        fields.put("billCycleMonthYear", 6);
        fields.put("billPhaseType", 2);
        fields.put("billMonthInd ", 1);
        fields.put("billAccrualIndicator", 1);
        fields.put("paymentSourceCode", 5);
        fields.put("discountOfferId", 10);
        fields.put("updateUserId", 8);
        fields.put("updateTimestamp", 26);

        this.lineAggregator = new FixedLengthLineAggregator(SummarySubLedgerDTO.class, fields);
    }

    @Test
    public void aggregateTest() {
        System.out.println("Testing subledger line aggregator - success");
        
        SummarySubLedgerDTO item = new SummarySubLedgerDTO();
        item.setFinancialEventNumber(456789);
        item.setFinancialmarketId("DUB");
        item.setFinancialCategory(789);
        item.setSubledgerTotalCreditAmount(-1234.78);
        item.setSubledgerTotalDebitAmount(86654.89);
        item.setUpdateUserId("WSBTest"); // default value is too long !!!
        
        String result = lineAggregator.aggregate(item);
        
        System.out.println(result);
        System.out.println("Line length: "+result.length());
        assertEquals(170, result.length()); //should it be 171 ???
    }    
}
