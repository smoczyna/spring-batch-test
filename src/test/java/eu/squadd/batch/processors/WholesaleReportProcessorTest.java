/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.readers.BilledBookingFileReader;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

/**
 *
 * @author smoczyna
 */
public class WholesaleReportProcessorTest {

    @Mock
    private SubLedgerProcessor tempSubLedgerOuput;

//    @Mock
//    protected StepExecution stepExecution;
//
//    @Mock
//    protected JobParameters jobParams;

    Environment environment;
    
    @InjectMocks
    private WholesaleReportProcessor wholesaleBookingProcessor = new WholesaleReportProcessor();
    
    BilledCsvFileDTO billedBookingRecord;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);                
        when(tempSubLedgerOuput.add()).thenCallRealMethod(); //thenReturn(createMockSubLedgerEntry());        
        billedBookingRecord = loadInputRecordFromCsvFile();        
    }

    private BilledCsvFileDTO loadInputRecordFromCsvFile() {
        //return new BilledBookingFileReader(environment, "bmdunld.csv"); // reader testing goes to separate test file
        BilledCsvFileDTO record = new BilledCsvFileDTO();
        record.setAirBillSeconds(1235);
        record.setAirProdId(1);
        record.setAirSurcharge(123.45d);
        record.setAirSurchargeProductId(1);
        record.setDeviceType("mobile");
        record.setFinancialMarket("Ireland");
        record.setHomeSbid("dublin");
        record.setIncompleteCallSurcharge(45.67d);
        record.setIncompleteInd("2");
        record.setIncompleteProdId(1);
        record.setInterExchangeCarrierCode(1);
        record.setLocalAirTax(12.23d);
        record.setMessageSource("B");
        record.setServingSbid("dublin");
        record.setSpace("space");
        record.setStateAirTax(34.45d);
        record.setTollBillSeconds(123);
        record.setTollProductId(1);
        record.setTollCharge(465.76d);
        record.setTollLocalTax(11.23d);
        record.setTollSurcharge(876.23d);
        record.setTollSurchargeProductId(2);
        record.setWholesaleOffpeakAirCharge(2345.67d);
        record.setWholesalePeakAirCharge(3456.78d);
        record.setWholesaleTollChargeLDOther(345.65d);
        record.setWholesaleTollChargeLDPeak(765.34d);
        record.setWholesaleUsageBytes(34567l);
        return record;
    }
    
    /**
     * Test of process method, of class WholesaleReportProcessor.
     * @throws java.lang.Exception
     */
    @Test
    public void testProcess() throws Exception {
        AggregateWholesaleReportDTO result = wholesaleBookingProcessor.process(billedBookingRecord);        
        assertNotNull(result);
    }

}
