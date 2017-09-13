/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;
import eu.squadd.batch.domain.casandra.FinancialMarket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class WholesaleReportProcessor implements ItemProcessor<BilledCsvFileDTO, AggregateWholesaleReportDTO> {

    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    String searchServingSbid = null;
    String searchHomeSbid = null;
    boolean homeEqualsServingSbid = false;
    
    @Override
    public AggregateWholesaleReportDTO process(BilledCsvFileDTO inRec) throws Exception {
        double tmpChargeAmt;
        final Set<Integer> PROD_IDS = new HashSet(Arrays.asList(new Integer[] {95, 1272, 12873, 13537, 13538, 36201}));
        int tmpProdId = 0;
        String messageSource = "B";
        boolean bypassBooking = false;
        boolean defaultBooking = false;
        int tmpInterExchangeCarrierCode = 0;
        
        // check if alternate booking is applicable but what is the consequence of it ???
        
        AggregateWholesaleReportDTO outRec = new AggregateWholesaleReportDTO();
        outRec.setBilledInd("Y");
                        
        //if (fileRecord.getDeviceType().contains(" ") //has no spaces (is valid) -> financial market to app financial market (is this a future PK ?)
        
        if (inRec.getAirProdId() > 0 && (inRec.getWholesalePeakAirCharge() > 0 || inRec.getWholesaleOffPeakAirCharge() >  0)) {
            outRec.setPeakDollarAmt(inRec.getWholesalePeakAirCharge());
            outRec.setOffPeakDollarAmt(inRec.getWholesaleOffPeakAirCharge());
            outRec.setDollarAmtOther(0d);
            outRec.setVoiceMinutes(inRec.getAirBillSeconds() * 60);
            tmpChargeAmt = inRec.getWholesalePeakAirCharge() + inRec.getWholesaleOffPeakAirCharge();
            if (inRec.getAirProdId()==190d)
                tmpProdId = 1;
            else
                tmpProdId = inRec.getAirProdId();
        }
        
        // second cassandra call goes here, it will check if product is wholesale product        
        String wholesaleBillingCode = null; // this object comes from db as response 
        //if (wholesaleBillingCode != null) it is
        //else it is not
        // what is that for ??? It's never used anywhere after 
        
        // move tmpChargeAmt to tmpWholesaleCost and tmpWholsaleSettlement ???
        
        // third cassandra call goes here, should retrieve unique row of FinancialEventCategory
        FinancialEventCategory financialEventCategory = null; // this object comes from db (just one) 
        if (!financialEventCategory.getBamsaffiliateindicator().equals("N")
                || !financialEventCategory.getCompanycode().contains(" ")
                || (financialEventCategory.getForeignservedindicator().equals("N") && searchHomeSbid.equals(searchServingSbid)))
            bypassBooking = true;
                
        if (financialEventCategory.getHomesidequalsservingsidindicator().trim().isEmpty() && messageSource.equals("M")) // applicable only for admin fees file 
            bypassBooking = false;
        
        /*
        whole below section is pointless:
        - checking alternate booking has no effect because it always qualify the record, regardless it is Y or N 
        - similarly both cases Y and N for Homesidequalsservingsidindicator apply too
        
        so below checks are never FALSE !!!
        
        // checking this: messageSource.equals("B") is pointless as it is always B
        if (financialEventCategory.getHomesidequalsservingsidindicator().trim().equals("Y") && homeEqualsServingSbid)
            bypassBooking = false;
        
        if (financialEventCategory.getHomesidequalsservingsidindicator().trim().equals("N") && !homeEqualsServingSbid 
                && !isAlternateBooking(inRec) && financialEventCategory.getAlternatebookingindicator().trim().equals("N")) 
            bypassBooking = false;        
        */ 
       
        /* default booking check - basically it means population of sub leadger record */        
        if (tmpProdId==0 || (PROD_IDS.contains(tmpProdId) && inRec.getInterExchangeCarrierCode()==0))
            defaultBooking = true;
        else
            tmpInterExchangeCarrierCode = 0; // it iis already intiialized with 0, no other values used
        
        // do the booking - create and populate sub leadger record
        //this.tempSubLedgerOuput.add(record);
        return outRec;
    }
    
    private boolean isAlternateBookingApplicable(BilledCsvFileDTO fileRecord) {
        boolean altBookingInd = false;
        String homeGlMarketId = null;
        String servingGlMarketId = null;
        
        searchHomeSbid = fileRecord.getHomeSbid();
        if (fileRecord.getServingSbid().trim().isEmpty()) 
            searchServingSbid = searchHomeSbid;
        else 
            searchHomeSbid = fileRecord.getServingSbid();
        
        if (searchHomeSbid.equals(searchServingSbid))
            homeEqualsServingSbid = true;
                
        /*
        call cassandra finacial market withbind params: fileRecord.getFinancialMarket() and homeEqServSbid (what column?)
        this call has to retrieve ust 1 record, if more records get back throw an error and move record to fail list
        */
        
        FinancialMarket financialMarket = null; // this object come from database as a response of above call
        String homeLegalEntityId = null;
        String servingLegalEntityId = null;
        
        if (financialMarket.getSidbid().equals(searchHomeSbid) && financialMarket.getAlternatebookingtype().equals("P")) {
            homeLegalEntityId = financialMarket.getGllegalentityid();
            if (homeEqualsServingSbid)
                altBookingInd = true;
        }
        
        if (!homeEqualsServingSbid && (financialMarket.getSidbid().equals(searchServingSbid) && financialMarket.getAlternatebookingtype().equals("P"))) {
             servingLegalEntityId = financialMarket.getGllegalentityid();
             if (homeLegalEntityId.equals(servingLegalEntityId))
                 altBookingInd = true;                 
        }
        
        if (financialMarket.getSidbid().equals(searchHomeSbid) && financialMarket.getAlternatebookingtype().equals("M")) {
            homeGlMarketId = financialMarket.getGlmarketid();
            if (homeEqualsServingSbid)
                altBookingInd = true;
        }
        
        if (!homeEqualsServingSbid && (financialMarket.getSidbid().equals(searchServingSbid) && financialMarket.getAlternatebookingtype().equals("M"))) {
             servingGlMarketId = financialMarket.getGlmarketid();
             if (homeGlMarketId.equals(servingGlMarketId))
                 altBookingInd = true;                 
        }
        
        return altBookingInd;
    }
}
