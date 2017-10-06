/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.utls;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.exceptions.ContentTooLongException;
import eu.squadd.batch.utils.FixedLengthLineAggregator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class WholesaleReportLineAggregatorTest {
    private FixedLengthLineAggregator lineAggregator;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
    @Before
    public void setUp() {
        Map<String, Integer> fields = new LinkedHashMap();
        fields.put("cycleMonthYear", 6);
        fields.put("startDate", 10);
        fields.put("endDate", 10);
        fields.put("homeLegalEntity", 5);
        fields.put("servingLegalEntity", 5);
        fields.put("homeFinancialMarketId", 3);
        fields.put("servingFinancialMarketId", 3);
        fields.put("productDiscountOfferId", 10);
        fields.put("contractTermId", 10);
        fields.put("peakDollarAmt", 12);
        fields.put("offpeakDollarAmt", 12);
        fields.put("voiceMinutes", 10);
        fields.put("tollDollarsAmt", 12);
        fields.put("tollMinutes", 10);
        fields.put("dollarAmt3G", 12);
        fields.put("usage3G", 12);
        fields.put("dollarAmt4G", 12);
        fields.put("usage4G", 12);
        fields.put("dollarAmtOther", 12);
        fields.put("dbCrInd", 2);
        fields.put("billedInd", 1);
        
        this.lineAggregator = new FixedLengthLineAggregator(AggregateWholesaleReportDTO.class, fields);
    }
    
    @Test
    public void aggregateTest() {
        System.out.println("Testing wholesale report line aggregator - success");
        
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("Y");
        record.setHomeFinancialMarketId("DUB");
        record.setCycleMonthYear("201709");
        record.setTollDollarsAmt(345.78);
        record.setProductDiscountOfferId(123);
        record.setUsage3G(123456L);
        record.setUsage4G(4566575L);
        record.setDbCrInd("DB");
        record.setStartDate(this.sdf.format(new Date()));
        
        String result = lineAggregator.aggregate(record);
        System.out.println(result);
        System.out.println("Line length: "+result.length());
        assertEquals(181, result.length());
    }
    
    @Test //(expected = ContentTooLongException.class)
    public void aggregateFailTest() {
        System.out.println("Testing wholesale report line aggregator - failure");
        
        AggregateWholesaleReportDTO record = new AggregateWholesaleReportDTO();
        record.setBilledInd("Y");
        record.setHomeFinancialMarketId("Dublin");
        record.setCycleMonthYear("201709");
        record.setTollDollarsAmt(345.78);
        record.setProductDiscountOfferId(123);
        record.setUsage3G(123456L);
        record.setUsage4G(4566575L);
        
        String result = lineAggregator.aggregate(record);
        //assertTrue(result==null);
    }
}
