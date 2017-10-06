/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.config.CassandraQueryManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BaseBookingInputInterface;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import eu.squadd.batch.domain.casandra.DataEvent;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;
import eu.squadd.batch.domain.casandra.FinancialMarket;
import eu.squadd.batch.domain.casandra.WholesalePrice;
import eu.squadd.batch.domain.exceptions.CassandraQueryException;
import eu.squadd.batch.domain.exceptions.MultipleRowsReturnedException;
import eu.squadd.batch.domain.exceptions.NoResultsReturnedException;
import eu.squadd.batch.utils.ProcessingUtils;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author smorcja
 * @param <T> - payload of the processor, 
 *              it must be a class implementing BaseBookingInputInterface or AdminFeeCsvFileDTO
 */
public class WholesaleReportProcessor<T> implements ItemProcessor<T, AggregateWholesaleReportDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleReportProcessor.class);
     
    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    @Autowired
    CassandraQueryManager queryManager;

    String searchServingSbid;
    String searchHomeSbid;
    boolean homeEqualsServingSbid;
    String financialMarket;
    String fileSource;
    int tmpProdId;
    double tmpChargeAmt;
    final Set<Integer> PROD_IDS_TOLL = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 36201}));
    final Set<Integer> PROD_IDS = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 13537, 13538, 36201}));

    /**
     * this method is a working version of booking logic, it is exact representation of the spec and need to be tuned when finished
     * @param inRec
     * @return
     * @throws Exception 
     */
    @Override
    public AggregateWholesaleReportDTO process(T inRec) throws Exception {
        this.searchServingSbid = null;
        this.searchHomeSbid = null;
        this.financialMarket = "";
        this.homeEqualsServingSbid = false;
        this.fileSource = null;
        this.tmpProdId = 0;
        this.tmpChargeAmt = 0;

        if (inRec instanceof BilledCsvFileDTO) 
            return processBilledRecord((BilledCsvFileDTO) inRec);
        else if (inRec instanceof UnbilledCsvFileDTO)
            return processUnbilledRecord((UnbilledCsvFileDTO) inRec);
        else if (inRec instanceof AdminFeeCsvFileDTO)
            return processAdminFeesRecord((AdminFeeCsvFileDTO) inRec); 
        else
            return null;
    }

    private void createSubLedgerRecord(Double tmpChargeAmt, FinancialEventCategory financialEventCategory, String financialMarket) {
        if (tmpChargeAmt==null || financialEventCategory==null || financialMarket==null)
            return;
        
        SummarySubLedgerDTO subLedgerOutput = this.tempSubLedgerOuput.addSubledger();
        
        // this is pointless, no used ever after
        // second cassandra call goes here, it will check if product is wholesale product        
        //String wholesaleBillingCode = null; // this object comes from db as response 
        //if (wholesaleBillingCode != null) it is
        //else it is not
        // what is that for ??? It's never used anywhere after 
        
        if (financialEventCategory.getFinancialeventnormalsign().equals("DR")) {
            if (financialEventCategory.getDebitcreditindicator().equals("DR")) {
                if (financialEventCategory.getBillingaccrualindicator().equals("Y")) {
                    if (tmpChargeAmt > 0) {
                        subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                    } else {
                        subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                    }
                } else if (financialEventCategory.getBillingaccrualindicator().equals("N")) {
                    if (tmpChargeAmt > 0) {
                        subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                    } else {
                        subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                        subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                    }
                }
            }
        }
        else if (financialEventCategory.getFinancialeventnormalsign().equals("CR")) {
            if (financialEventCategory.getDebitcreditindicator().equals("CR")) {
                if (tmpChargeAmt > 0) {
                    subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                } else {
                    subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                }
            }
            else if (financialEventCategory.getDebitcreditindicator().equals("DR")) {
                if (tmpChargeAmt > 0) {
                    subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                } else {
                    subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                }
            }
        }
        
        subLedgerOutput.setFinancialEventNumber(financialEventCategory.getFinancialeventnumber());
        subLedgerOutput.setFinancialCategory(financialEventCategory.getFinancialcategory());
        subLedgerOutput.setFinancialmarketId(financialMarket);
        subLedgerOutput.setBillCycleMonthYear(ProcessingUtils.getYearAndMonthFromStrDate(this.tempSubLedgerOuput.getDates().getRptPerEndDate()));
        subLedgerOutput.setBillAccrualIndicator(financialEventCategory.getBillingaccrualindicator());
 
        this.createOffsetBooking(subLedgerOutput);    
    }

    private void createOffsetBooking(SummarySubLedgerDTO subLedgerOutput) {
        Integer offsetFinCat = this.tempSubLedgerOuput.findOffsetFinCat(subLedgerOutput.getFinancialEventNumber());
        if (offsetFinCat==null)
            LOGGER.error("Offset fin cat value not found !!!");
        else {
            subLedgerOutput.setFinancialCategory(offsetFinCat);
            Double debitAmt = subLedgerOutput.getSubledgerTotalDebitAmount();
            Double creditAmt = subLedgerOutput.getSubledgerTotalCreditAmount();
            SummarySubLedgerDTO clone = null;
            try {
                clone = subLedgerOutput.clone();
            } catch (CloneNotSupportedException ex) {
                LOGGER.error("Failed to create offset booking !!!");
            }    
            clone.setSubledgerTotalDebitAmount(creditAmt);
            clone.setSubledgerTotalCreditAmount(debitAmt);
            this.tempSubLedgerOuput.addOffsetSubledger(clone);
        }            
    }

    private boolean isAlternateBookingApplicable(BaseBookingInputInterface inRec) {
        boolean altBookingInd = false;
        String homeGlMarketId = null;
        String servingGlMarketId;
        
        searchHomeSbid = inRec.getHomeSbid();
        if (inRec.getServingSbid().trim().isEmpty()) {
            searchServingSbid = searchHomeSbid;
        } else {
            searchHomeSbid = inRec.getServingSbid();
        }
        if (searchHomeSbid.equals(searchServingSbid)) {
            homeEqualsServingSbid = true;
        }

        FinancialMarket finMarket = this.getFinancialMarketFromDb(this.financialMarket);
                
        String homeLegalEntityId = null;
        String servingLegalEntityId;

        if (finMarket.getSidbid().equals(searchHomeSbid) && finMarket.getAlternatebookingtype().equals("P")) {
            homeLegalEntityId = finMarket.getGllegalentityid();
            if (homeEqualsServingSbid) {
                altBookingInd = true;
            }
        }

        if (!homeEqualsServingSbid && (finMarket.getSidbid().equals(searchServingSbid) && finMarket.getAlternatebookingtype().equals("P"))) {
            servingLegalEntityId = finMarket.getGllegalentityid();
            if (homeLegalEntityId.equals(servingLegalEntityId)) {
                altBookingInd = true;
            }
        }

        if (finMarket.getSidbid().equals(searchHomeSbid) && finMarket.getAlternatebookingtype().equals("M")) {
            homeGlMarketId = finMarket.getGlmarketid();
            if (homeEqualsServingSbid) {
                altBookingInd = true;
            }
        }

        if (!homeEqualsServingSbid && (finMarket.getSidbid().equals(searchServingSbid) && finMarket.getAlternatebookingtype().equals("M"))) {
            servingGlMarketId = finMarket.getGlmarketid();
            if (homeGlMarketId.equals(servingGlMarketId)) {
                altBookingInd = true;
            }
        }
        return altBookingInd;
    }
    
    private boolean bypassBooking(FinancialEventCategory financialEventCategory, boolean altBookingInd) {        
        boolean bypassBooking = false;
        
        // bypass booking check stage 1
        if (!financialEventCategory.getBamsaffiliateindicator().equals("N") || !financialEventCategory.getCompanycode().trim().isEmpty()) {
            bypassBooking = true;
        }
        
        // bypass booking check stage 2
        if (this.fileSource.equals("M") && financialEventCategory.getHomesidequalsservingsidindicator().trim().isEmpty())
            bypassBooking = false;
               
        if (this.fileSource.equals("U") && financialEventCategory.getBillingaccrualindicator().equals("Y"))
            bypassBooking = false;

        if (this.fileSource.equals("B")) {
            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("Y") && searchHomeSbid.equals(searchServingSbid))
                bypassBooking = false;
            
            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("N") && !searchHomeSbid.equals(searchServingSbid)) {
                if ((financialEventCategory.getAlternatebookingindicator().equals("N") && !altBookingInd)
                     || (financialEventCategory.getAlternatebookingindicator().equals("Y") && altBookingInd))
                    bypassBooking = false;
            }                         
        }
        
        // bypass booking check stage 3
        if (bypassBooking && (searchHomeSbid.equals(searchServingSbid))) {
            if (!financialEventCategory.getForeignservedindicator().trim().isEmpty() || financialEventCategory.getForeignservedindicator().equals("N"))
                bypassBooking = true;
        }
        
        return bypassBooking;
    }
    
    private AggregateWholesaleReportDTO processBilledRecord(BilledCsvFileDTO billedRec) {
        AggregateWholesaleReportDTO outRec = new AggregateWholesaleReportDTO();
        //int tmpInterExchangeCarrierCode = 0;
        boolean bypassBooking;
        boolean altBookingInd;
        //boolean defaultBooking;
        
        outRec.setBilledInd("Y");
        this.fileSource = "B";
 
        //if (billedRec.getDeviceType().trim().isEmpty())
        if (billedRec.getFinancialMarket().trim().isEmpty())
            this.financialMarket = "HUB";
        else
            this.financialMarket = billedRec.getFinancialMarket();
        
        altBookingInd = this.isAlternateBookingApplicable(billedRec);
        
        if (billedRec.getAirProdId() > 0 && (billedRec.getWholesalePeakAirCharge() > 0 || billedRec.getWholesaleOffpeakAirCharge() > 0)) {
            outRec.setPeakDollarAmt(billedRec.getWholesalePeakAirCharge());
            outRec.setOffpeakDollarAmt(billedRec.getWholesaleOffpeakAirCharge());
            outRec.setDollarAmtOther(0d);
            outRec.setVoiceMinutes(billedRec.getAirBillSeconds() * 60);
            tmpChargeAmt = billedRec.getWholesalePeakAirCharge() + billedRec.getWholesaleOffpeakAirCharge();
            if (billedRec.getAirProdId().equals(190))
                this.tmpProdId = 1;
            else
                this.tmpProdId = billedRec.getAirProdId();            
        }
        
        if ((billedRec.getTollProductId() > 0 && billedRec.getTollCharge() > 0)
            || billedRec.getInterExchangeCarrierCode().equals(5050)
            || billedRec.getIncompleteInd().equals("D")
            || !billedRec.getHomeSbid().equals(billedRec.getServingSbid())
            || (billedRec.getAirProdId().equals(190) && billedRec.getWholesaleTollChargeLDPeak() > 0 && billedRec.getWholesaleTollChargeLDOther() > 0)) {

            if (billedRec.getAirProdId().equals(190)) {
                this.tmpProdId = 95;
            } else {
                this.tmpProdId = billedRec.getTollProductId();
            }

            if (billedRec.getIncompleteInd().equals("D")) {
                this.tmpChargeAmt = billedRec.getTollCharge();
                outRec.setDollarAmtOther(this.tmpChargeAmt);

                DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);
                if (dataEvent==null)
                    LOGGER.error("Data Event record came back null");
                
                /* compute data usage */
                if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                    outRec.setDollarAmt3G(this.tmpChargeAmt);
                    outRec.setUsage3G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                }    
                else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                    outRec.setDollarAmt4G(this.tmpChargeAmt);
                    outRec.setUsage4G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                }
            } else {
                tmpChargeAmt = billedRec.getWholesaleTollChargeLDPeak() + billedRec.getWholesaleTollChargeLDOther();
                outRec.setTollDollarsAmt(this.tmpChargeAmt);
                outRec.setTollMinutes(this.tmpProdId);
                outRec.setTollMinutes(Math.round(billedRec.getTollBillSeconds() / 60));
            }
        }
        outRec.setPeakDollarAmt(0d);
        
//        if (PROD_IDS_TOLL.contains(this.tmpProdId))
//            tmpInterExchangeCarrierCode = billedRec.getInterExchangeCarrierCode();
        
        
        /* do events & book record */   
        FinancialEventCategory financialEventCategory = this.getEventCategoryFromDb(this.tmpProdId, this.homeEqualsServingSbid ? "Y" : "N", altBookingInd);
        if (financialEventCategory==null)
            LOGGER.error("Financial Event Category for params: prodid=" + this.tmpProdId + ", homeEqServing=" + (this.homeEqualsServingSbid ? "Y" : "N") + ", altBooking=" + (altBookingInd ? "Y" : "N"));
            
        bypassBooking = this.bypassBooking(financialEventCategory, altBookingInd);

        /* default booking check - basically it means population of sub leadger record */   
        /* this rule here works only for billed booking */
        
// this is dummy code, never used anywhere after
//        if (this.tmpProdId == 0 || (PROD_IDS.contains(this.tmpProdId) && billedRec.getInterExchangeCarrierCode() == 0)) {
//            defaultBooking = true;
//        } else {
//            tmpInterExchangeCarrierCode = 0; // it is already intiialized with 0, no other values used
//        }

        if (bypassBooking)
            LOGGER.warn("Booking bypass detected, record skipped for sub ledger file ...");
        else
            this.createSubLedgerRecord(tmpChargeAmt, financialEventCategory, financialMarket);
                
        return outRec;
    }
    
    private AggregateWholesaleReportDTO processUnbilledRecord(UnbilledCsvFileDTO unbilledRec) {        
        if (unbilledRec.getAirProdId() > 0 && (unbilledRec.getWholesalePeakAirCharge() > 0 || unbilledRec.getWholesaleOffpeakAirCharge() > 0)) {
            AggregateWholesaleReportDTO outRec = new AggregateWholesaleReportDTO();
            boolean altBookingInd;
            outRec.setBilledInd("N");
            this.fileSource = "U";
            financialMarket = unbilledRec.getFinancialMarket();
            
            altBookingInd = this.isAlternateBookingApplicable(unbilledRec);
            
            this.searchHomeSbid = unbilledRec.getHomeSbid();
            if (unbilledRec.getServingSbid().trim().isEmpty())
                this.searchServingSbid = unbilledRec.getHomeSbid();
            else
                this.searchServingSbid = unbilledRec.getServingSbid();

            if (this.searchHomeSbid.equals(this.searchServingSbid))
                this.homeEqualsServingSbid = true;

            if (unbilledRec.getAirProdId().equals(190))
                this.tmpProdId = 1;
            else
                this.tmpProdId = unbilledRec.getAirProdId();
            
            this.tmpChargeAmt = unbilledRec.getWholesalePeakAirCharge() + unbilledRec.getWholesaleOffpeakAirCharge();
            if (unbilledRec.getMessageSource().trim().isEmpty()) {
                outRec.setPeakDollarAmt(unbilledRec.getWholesalePeakAirCharge());
                outRec.setDollarAmtOther(0d);
                outRec.setVoiceMinutes(Math.round(unbilledRec.getAirBillSeconds() / 60));
            }
            else if (unbilledRec.getMessageSource().equals("D")) {
                outRec.setDollarAmtOther(unbilledRec.getWholesalePeakAirCharge());
                outRec.setPeakDollarAmt(0d);
            }
            outRec.setOffpeakDollarAmt(unbilledRec.getWholesaleOffpeakAirCharge());
            
            DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);
            
            if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                outRec.setDollarAmt3G(this.tmpChargeAmt);
                outRec.setUsage3G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
            }    
            else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                outRec.setDollarAmt4G(this.tmpChargeAmt);
                outRec.setUsage4G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
            }
            
            FinancialEventCategory financialEventCategory = this.getEventCategoryFromDb(this.tmpProdId, this.homeEqualsServingSbid ? "Y" : "N", altBookingInd); 
            this.createSubLedgerRecord(tmpChargeAmt, financialEventCategory, financialMarket);            
            return outRec;
        }
        else
            return null;
    }
    
    private AggregateWholesaleReportDTO processAdminFeesRecord(AdminFeeCsvFileDTO adminFeesRec) {
        AggregateWholesaleReportDTO outRec = new AggregateWholesaleReportDTO();
        outRec.setBilledInd("Y");
        this.fileSource = "M";
        this.searchHomeSbid = adminFeesRec.getSbid(); // there is no check if both home and serving bids are equal ???
        this.tmpProdId = adminFeesRec.getProductId();
        this.financialMarket = adminFeesRec.getFinancialMarket();
        
        // call cassandra wholesale price table
        WholesalePrice wholesalePrice = this.getWholesalePriceFromDb(this.tmpProdId, this.searchHomeSbid);
        
        this.tmpChargeAmt = wholesalePrice.getProductwholesaleprice().multiply(BigDecimal.valueOf(adminFeesRec.getAdminCount()).movePointLeft(2)).floatValue();
        outRec.setDollarAmtOther(this.tmpChargeAmt);
        
        boolean altBookingInd = false; // alternate booking cannot be checked here due incompatible payload (adminfees file doesn't fit the interface as it has no all required fields)
        FinancialEventCategory financialEventCategory = this.getEventCategoryFromDb(this.tmpProdId, " ", altBookingInd);
                
        // this is pointless - it's false by default
        //if (financialEventCategory.getHomesidequalsservingsidindicator().trim().isEmpty())
        //    bypassBooking = false;
        //else
              
        boolean bypassBooking = this.bypassBooking(financialEventCategory, altBookingInd);
        
        if (bypassBooking)
            LOGGER.warn("Booking bypass detected, record skipped for sub ledger file ...");
        else
            this.createSubLedgerRecord(tmpChargeAmt, financialEventCategory, financialMarket);
        
        return outRec;
    }
    
    protected FinancialMarket getFinancialMarketFromDb(String financialMarketId) {
        FinancialMarket result = null;
        try {
            List<FinancialMarket> dbResult = queryManager.getFinancialMarketRecord(financialMarketId);
            if (dbResult.size()==1)
                result = dbResult.get(0);
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return result;
    }
    
    protected FinancialEventCategory getEventCategoryFromDb(Integer tmpProdId, String homeEqualsServingSbid, boolean altBookingInd) {
        FinancialEventCategory result = null;
        try {
            List<FinancialEventCategory> dbResult = queryManager.getFinancialEventCategoryNoClusteringRecord(tmpProdId, homeEqualsServingSbid, altBookingInd ? "Y" : "N");
            if (dbResult.size()==1)
                result = dbResult.get(0);
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return result;
    }
    
    protected DataEvent getDataEventFromDb(Integer productId) {
        DataEvent result = null;
        try {
            List<DataEvent> dbResult = queryManager.getDataEventRecords(productId);
            if (dbResult.size()==1)
                result = dbResult.get(0);
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return result;
    }
    
    protected WholesalePrice getWholesalePriceFromDb(Integer tmpProdId, String searchHomeSbid) {
        WholesalePrice result = null;
        try {
            List<WholesalePrice> dbResult = queryManager.getWholesalePriceRecords(tmpProdId, searchHomeSbid);
            if (dbResult.size()==1)
                result = dbResult.get(0);
        } catch (MultipleRowsReturnedException | NoResultsReturnedException | CassandraQueryException ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return result;
    }
}
