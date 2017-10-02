/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import eu.squadd.batch.domain.casandra.DataEvent;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;
import eu.squadd.batch.domain.casandra.WholesalePrice;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smoczyna
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class WholesaleReportProcessorTest {

    @Mock
    private SubLedgerProcessor tempSubLedgerOuput;

    @InjectMocks
    private final WholesaleReportProcessor wholesaleBookingProcessor = new WholesaleReportProcessor();
    
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);                
        when(tempSubLedgerOuput.addSubledger()).thenReturn(new SummarySubLedgerDTO());
        when(tempSubLedgerOuput.getDates()).thenReturn(this.createBookDateRecord());
         
//  following stuff need to be stubbed when the real db calls will be in place, right now it is exactly the same   
//        when(wholesaleBookingProcessor.getEventCategoryFromDb()).thenReturn(this.createEventCategory(true));
//        when(wholesaleBookingProcessor.getDataEventFromDb()).thenReturn(this.createDataEvent(true));
//        when(wholesaleBookingProcessor.getWholesalePriceFromDb()).thenReturn(this.createWholesalePrice());
    }

    private BookDateCsvFileDTO createBookDateRecord() {
        BookDateCsvFileDTO bookDates = new BookDateCsvFileDTO();
        bookDates.setRptPerStartDate("08/01/2017");
        bookDates.setRptPerEndDate("08/31/2017");
        bookDates.setTransPerStartDate("07/26/2017");
        bookDates.setTransPerEndDate("08/25/2017");
        return bookDates;
    }
    
    private BilledCsvFileDTO createBilledBookingsInputRecord() {
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
    
    private UnbilledCsvFileDTO createUnbilledBookingsInputRecord() {
        UnbilledCsvFileDTO record = new UnbilledCsvFileDTO();
        record.setHomeSbid("athlone");
        record.setServingSbid("dublin");
        record.setAirBillSeconds(457687);
        record.setAirProdId(100);
        record.setFinancialMarket("Ireland");
        record.setMessageSource("U");
        record.setSource("unit test");
        record.setTotalWholesaleUsage(34878L);
        record.setWholesaleOffpeakAirCharge(3465.87);
        record.setWholesalePeakAirCharge(464567.87);
        return record;
    }
    
    private AdminFeeCsvFileDTO createAdminFeesBookingInputRecord() {
        AdminFeeCsvFileDTO record = new AdminFeeCsvFileDTO();
        record.setSbid("galway");
        record.setProductId(123);
        record.setFinancialMarket("Ireland");
        record.setAdminChargeAmt(34756.87);
        record.setAdminCount(7867);
        return record;
    }
    
    protected FinancialEventCategory createEventCategory(boolean validForBooking) {
        FinancialEventCategory financialEventCategory = new FinancialEventCategory();
        financialEventCategory.setBamsaffiliateindicator("N");
        if (validForBooking) financialEventCategory.setCompanycode(" ");
        else financialEventCategory.setCompanycode("CDN");         // value here causes booking bypass for 'B' file
        financialEventCategory.setForeignservedindicator("Y");
        financialEventCategory.setHomesidequalsservingsidindicator("Y");
        financialEventCategory.setFinancialeventnormalsign("DR");
        financialEventCategory.setDebitcreditindicator("DR");
        financialEventCategory.setBillingaccrualindicator("Y");
        financialEventCategory.setFinancialeventnumber(4756);
        financialEventCategory.setFinancialcategory(678);
        financialEventCategory.setFinancialmarketid("FM1");
        financialEventCategory.setAlternatebookingindicator("Z");        
        return financialEventCategory;
    } 
    
    protected DataEvent createDataEvent(boolean is3G) {
        DataEvent event = new DataEvent();
        event.setProductid(100);
        if (is3G) event.setDataeventsubtype("DEFLT"); // 3G        
        else event.setDataeventsubtype("DEF4G"); // 4G
        return event;
    }
    
    protected WholesalePrice createWholesalePrice() {
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setProductwholesaleprice(351.45);
        return wholesalePrice;
    }
    
    /**
     * Test of process method, of class WholesaleReportProcessor.
     * @throws java.lang.Exception
     */
    //@Test
    public void testBilledBookingProcess() throws Exception {
        BilledCsvFileDTO billedBookingRecord = createBilledBookingsInputRecord();
        AggregateWholesaleReportDTO result = wholesaleBookingProcessor.process(billedBookingRecord);
        verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNotNull(result);
        //assertTrue(tempSubLedgerOuput.getAggregatedOutput().size()>0);
    }

    //@Test
    public void testUnbilledBookingProcess() throws Exception {
        UnbilledCsvFileDTO unbilledBookingRecord = createUnbilledBookingsInputRecord();
        AggregateWholesaleReportDTO result = wholesaleBookingProcessor.process(unbilledBookingRecord);
        verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNotNull(result);
    }
    
    //@Test
    public void testAdmiFeesBookingProcess() throws Exception {
        AdminFeeCsvFileDTO adminFeesBookingRecord = createAdminFeesBookingInputRecord();
        AggregateWholesaleReportDTO result = wholesaleBookingProcessor.process(adminFeesBookingRecord);
        verify(tempSubLedgerOuput, times(1)).addSubledger();
        assertNotNull(result);
    }
}
