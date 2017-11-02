/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;


import eu.squadd.batch.config.MongoQueryManager;
import eu.squadd.batch.utils.WholesaleBookingProcessorHelper;
import eu.squadd.batch.constants.Constants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BaseBookingInputInterface;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.MinBookingInterface;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import eu.squadd.batch.domain.WholesaleProcessingOutput;
import eu.squadd.batch.domain.mongo.DataEvent;
import eu.squadd.batch.domain.mongo.FinancialEventCategory;
import eu.squadd.batch.domain.mongo.FinancialMarket;
import eu.squadd.batch.domain.mongo.WholesalePrice;
import eu.squadd.batch.utils.ProcessingUtils;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smorcja
 * @param <T> - payload of the processor, 
 *              it must be a class implementing BaseBookingInputInterface or AdminFeeCsvFileDTO
 */
public class WholesaleBookingProcessor<T> implements ItemProcessor<T, WholesaleProcessingOutput> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleBookingProcessor.class);

    @Autowired
    WholesaleBookingProcessorHelper processingHelper;

    @Autowired
    MongoQueryManager queryManager;

    String searchServingSbid;
    String searchHomeSbid;
    boolean homeEqualsServingSbid;
    String financialMarket;
    String fileSource;
    int tmpProdId;
    double tmpChargeAmt;
    int tmpInterExchangeCarrierCode;
    final Set<Integer> PROD_IDS_TOLL = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 36201}));
    final Set<Integer> PROD_IDS = new HashSet(Arrays.asList(new Integer[]{95, 12872, 12873, 13537, 13538, 36201}));

    /**
     * this method is a working version of booking logic, it is exact
     * representation of the spec and need to be tuned when finished
     *
     * @param inRec
     * @return
     * @throws Exception
     */
    @Override
    public WholesaleProcessingOutput process(T inRec) throws Exception {
        this.searchServingSbid = null;
        this.searchHomeSbid = null;
        this.financialMarket = "";
        this.homeEqualsServingSbid = false;
        this.fileSource = null;
        this.tmpProdId = 0;
        this.tmpChargeAmt = 0;
        this.tmpInterExchangeCarrierCode = 0;

        if (inRec instanceof BilledCsvFileDTO) {
            return processBilledRecord((BilledCsvFileDTO) inRec);
        } else if (inRec instanceof UnbilledCsvFileDTO) {
            return processUnbilledRecord((UnbilledCsvFileDTO) inRec);
        } else if (inRec instanceof AdminFeeCsvFileDTO) {
            return processAdminFeesRecord((AdminFeeCsvFileDTO) inRec);
        } else {
            return null;
        }
    }

    private SummarySubLedgerDTO createSubLedgerBooking(Double tmpChargeAmt, FinancialEventCategory financialEventCategory, String financialMarket, String dbcrIndicatorFromFile) {
        if (tmpChargeAmt == null || financialEventCategory == null || financialMarket == null) {
            return null;
        }
        SummarySubLedgerDTO subLedgerOutput = this.processingHelper.addSubledger();

        if (financialEventCategory.getFinancialeventnormalsign().equals(Constants.DEBIT_CODE)) {
            if (financialEventCategory.getDebitcreditindicator().equals(Constants.DEBIT_CODE)) {
                if (financialEventCategory.getBillingaccrualindicator().equals("Y") || dbcrIndicatorFromFile.equals("DR")) {
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
        } else if (financialEventCategory.getFinancialeventnormalsign().equals(Constants.CREDIT_CODE)) {
            if (financialEventCategory.getDebitcreditindicator().equals(Constants.CREDIT_CODE)) {
                if (tmpChargeAmt > 0) {
                    subLedgerOutput.setSubledgerTotalCreditAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalDebitAmount(0d);
                } else {
                    subLedgerOutput.setSubledgerTotalDebitAmount(tmpChargeAmt);
                    subLedgerOutput.setSubledgerTotalCreditAmount(0d);
                }
            } else if (financialEventCategory.getDebitcreditindicator().equals(Constants.DEBIT_CODE)) {
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
        subLedgerOutput.setBillCycleMonthYear(ProcessingUtils.getYearAndMonthFromStrDate(this.processingHelper.getDates().getRptPerEndDate()));
        subLedgerOutput.setBillAccrualIndicator(financialEventCategory.getBillingaccrualindicator());
        return subLedgerOutput;
    }

    private SummarySubLedgerDTO createOffsetBooking(SummarySubLedgerDTO subLedgerOutput) {
        SummarySubLedgerDTO clone = null;
        Integer offsetFinCat = this.processingHelper.findOffsetFinCat(subLedgerOutput.getFinancialEventNumber());
        if (offsetFinCat == null) {
            LOGGER.error(Constants.OFFSET_NOT_FOUND);
        } else {
            Double debitAmt = subLedgerOutput.getSubledgerTotalDebitAmount();
            Double creditAmt = subLedgerOutput.getSubledgerTotalCreditAmount();

            if (debitAmt == 0d && creditAmt == 0d) {
                LOGGER.info(Constants.ZERO_SUBLEDGER_AMOUNT);
            }

            try {
                clone = subLedgerOutput.clone();
            } catch (CloneNotSupportedException ex) {
                LOGGER.error(Constants.FAILED_TO_CREATE_OFFSET);
            }
            clone.setFinancialCategory(offsetFinCat);
            clone.setSubledgerTotalDebitAmount(creditAmt);
            clone.setSubledgerTotalCreditAmount(debitAmt);
        }
        this.processingHelper.incrementCounter(Constants.SUBLEDGER);
        return clone;
    }

    private boolean isAlternateBookingApplicable(BaseBookingInputInterface inRec) {
        boolean altBookingInd = false;
        String homeGlMarketId = " ";
        String servingGlMarketId;

        searchHomeSbid = inRec.getHomeSbid();
        if (inRec.getServingSbid().trim().isEmpty()) {
            searchServingSbid = searchHomeSbid;
        } else {
            searchServingSbid = inRec.getServingSbid();
        }
        if (searchHomeSbid.equals(searchServingSbid)) {
            homeEqualsServingSbid = true;
        }

        FinancialMarket finMarket = this.getFinancialMarketFromDb(this.financialMarket);

        String homeLegalEntityId = " ";
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
        //altBookingInd = false; // temporary fix, will be reviewed later
        return altBookingInd;
    }

    private boolean bypassBooking(FinancialEventCategory financialEventCategory, boolean altBookingInd) {
        boolean bypassBooking = false;

        if (!financialEventCategory.getBamsaffiliateindicator().equals("N") || !financialEventCategory.getCompanycode().trim().isEmpty()) {
            bypassBooking = true;
        }

        if (this.fileSource.equals("M") && financialEventCategory.getHomesidequalsservingsidindicator().trim().isEmpty()) {
            bypassBooking = false;
        }

        if (this.fileSource.equals("U") && financialEventCategory.getBillingaccrualindicator().equals("Y")) {
            bypassBooking = false;
        }

        if (this.fileSource.equals("B")) {
            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("Y") && searchHomeSbid.equals(searchServingSbid)) {
                bypassBooking = false;
            }

            if (financialEventCategory.getHomesidequalsservingsidindicator().equals("N") && !searchHomeSbid.equals(searchServingSbid)) {
                if ((financialEventCategory.getAlternatebookingindicator().equals("N") && !altBookingInd)
                        || (financialEventCategory.getAlternatebookingindicator().equals("Y") && altBookingInd)) {
                    bypassBooking = false;
                }
            }
        }
        
        if (bypassBooking && (searchHomeSbid.equals(searchServingSbid))) {
            if (!financialEventCategory.getForeignservedindicator().trim().isEmpty() || financialEventCategory.getForeignservedindicator().equals("N")) {
                bypassBooking = true;
            }
        }
        return bypassBooking;
    }

    private void makeBookings(MinBookingInterface inRec, WholesaleProcessingOutput outRec, int iecCode) {
        boolean altBookingInd = false;
        String tmpHomeEqualsServingSbid = " ";
        if (inRec instanceof BilledCsvFileDTO) {
            altBookingInd = this.isAlternateBookingApplicable((BilledCsvFileDTO) inRec);
            tmpHomeEqualsServingSbid = this.homeEqualsServingSbid ? "Y" : "N";
        }
        FinancialEventCategory financialEventCategory;
        financialEventCategory = this.getEventCategoryFromDb(this.tmpProdId, tmpHomeEqualsServingSbid, altBookingInd, iecCode, inRec.getDebitcreditindicator());
        boolean bypassBooking = this.bypassBooking(financialEventCategory, altBookingInd);
        if (bypassBooking) {
            LOGGER.warn(Constants.BOOKING_BYPASS_DETECTED);
            this.processingHelper.incrementCounter(Constants.BYPASS);
        } else {
            if (tmpChargeAmt == 0) {
                LOGGER.warn(Constants.ZERO_CHARGES_DETECTED);
                this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            } else {
                SummarySubLedgerDTO subledger = this.createSubLedgerBooking(tmpChargeAmt, financialEventCategory, financialMarket, inRec.getDebitcreditindicator());
                outRec.addSubledgerRecord(subledger);
                outRec.addSubledgerRecord(this.createOffsetBooking(subledger));
            }
        }
    }
    
    private WholesaleProcessingOutput processBilledRecord(BilledCsvFileDTO billedRec) {
        WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
        AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
        
        boolean zeroAirCharge = false;
        boolean zeroTollCharge = false;

        report.setBilledInd("Y");
        this.fileSource = "B";

        if (billedRec.getFinancialMarket().trim().isEmpty()) {
            this.financialMarket = "HUB";
        } else {
            this.financialMarket = billedRec.getFinancialMarket();
        }

        if (billedRec.getWholesalePeakAirCharge() == 0 && billedRec.getWholesaleOffpeakAirCharge() == 0 && billedRec.getTollCharge() == 0) {
            LOGGER.info(Constants.ZERO_CHARGES_SKIP);
            this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            return null;
        }

        if (billedRec.getAirProdId() > 0 && (billedRec.getWholesalePeakAirCharge() > 0 || billedRec.getWholesaleOffpeakAirCharge() > 0)) {
            report.setPeakDollarAmt(billedRec.getWholesalePeakAirCharge());
            report.setOffpeakDollarAmt(billedRec.getWholesaleOffpeakAirCharge());
            report.setDollarAmtOther(0d);
            report.setVoiceMinutes(billedRec.getAirBillSeconds() * 60);
            tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getWholesalePeakAirCharge() + billedRec.getWholesaleOffpeakAirCharge());

            if (billedRec.getAirProdId().equals(190)) {
                this.tmpProdId = 1;
            } else {
                this.tmpProdId = billedRec.getAirProdId();
            }
            report.setPeakDollarAmt(0d);            
            this.makeBookings(billedRec, outRec, tmpInterExchangeCarrierCode);
        } else {
            zeroAirCharge = true;
        }

        if (billedRec.getTollProductId() > 0 && billedRec.getTollCharge() > 0) {
            if ((billedRec.getInterExchangeCarrierCode().equals(5050) && billedRec.getDebitcreditindicator().equals("CR"))
                    || billedRec.getIncompleteInd().equals("D")
                    || (!billedRec.getHomeSbid().equals(billedRec.getServingSbid()) && billedRec.getDebitcreditindicator().equals("CR"))
                    || (billedRec.getAirProdId().equals(190) && billedRec.getWholesaleTollChargeLDPeak() > 0 && billedRec.getWholesaleTollChargeLDOther() > 0)) {

                if (billedRec.getAirProdId().equals(190)) {
                    this.tmpProdId = 95;
                } else {
                    this.tmpProdId = billedRec.getTollProductId();
                }

                if (billedRec.getInterExchangeCarrierCode().equals(5050)) {
                    this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getTollCharge());
                    report.setDollarAmtOther(this.tmpChargeAmt);
                } else if (billedRec.getIncompleteInd().equals("D")) {
                    this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getTollCharge());
                    report.setDollarAmtOther(this.tmpChargeAmt);

                    DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);

                    /* compute data usage */
                    if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                        report.setDollarAmt3G(this.tmpChargeAmt);
                        report.setUsage3G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                    } else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                        report.setDollarAmt4G(this.tmpChargeAmt);
                        report.setUsage4G(Math.round(billedRec.getWholesaleUsageBytes().doubleValue() / 1024));
                    }
                } else {
                    tmpChargeAmt = ProcessingUtils.roundDecimalNumber(billedRec.getWholesaleTollChargeLDPeak() + billedRec.getWholesaleTollChargeLDOther());
                    report.setTollDollarsAmt(this.tmpChargeAmt);
                    report.setTollMinutes(this.tmpProdId);
                    report.setTollMinutes(Math.round(billedRec.getTollBillSeconds() / 60));
                }
                if (!billedRec.getInterExchangeCarrierCode().equals(5050)) {
                    tmpInterExchangeCarrierCode = 0;
                } else {
                    tmpInterExchangeCarrierCode = billedRec.getInterExchangeCarrierCode();
                }

                report.setPeakDollarAmt(0d);
                outRec.addWholesaleReportRecord(report);
                this.makeBookings(billedRec, outRec, tmpInterExchangeCarrierCode);
            } else {
                LOGGER.info(Constants.GAP_DETECTED);
                this.processingHelper.incrementCounter(Constants.GAPS);
            }
        } else {
            zeroTollCharge = true;
        }

        if (zeroAirCharge && zeroTollCharge) {
            LOGGER.info(Constants.INVALID_INPUT);
            this.processingHelper.incrementCounter(Constants.DATA_ERRORS);
            return null;
        }
        return outRec;
    }

    private WholesaleProcessingOutput processUnbilledRecord(UnbilledCsvFileDTO unbilledRec) {
        if (unbilledRec.getAirProdId() > 0 && (unbilledRec.getWholesalePeakAirCharge() > 0 || unbilledRec.getWholesaleOffpeakAirCharge() > 0)) {
            WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
            AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
            
            report.setBilledInd("N");
            this.fileSource = "U";
            financialMarket = unbilledRec.getFinancialMarket();

            this.searchHomeSbid = unbilledRec.getHomeSbid();
            if (unbilledRec.getServingSbid().trim().isEmpty()) {
                this.searchServingSbid = unbilledRec.getHomeSbid();
            } else {
                this.searchServingSbid = unbilledRec.getServingSbid();
            }
            if (this.searchHomeSbid.equals(this.searchServingSbid)) {
                this.homeEqualsServingSbid = true;
            }
            if (unbilledRec.getAirProdId().equals(190)) {
                this.tmpProdId = 1;
            } else {
                this.tmpProdId = unbilledRec.getAirProdId();
            }
            this.tmpChargeAmt = ProcessingUtils.roundDecimalNumber(unbilledRec.getWholesalePeakAirCharge() + unbilledRec.getWholesaleOffpeakAirCharge());
            if (unbilledRec.getMessageSource().trim().isEmpty()) {
                report.setPeakDollarAmt(unbilledRec.getWholesalePeakAirCharge());
                report.setDollarAmtOther(0d);
                report.setVoiceMinutes(Math.round(unbilledRec.getAirBillSeconds() / 60));
            } else if (unbilledRec.getMessageSource().equals("D")) {
                report.setDollarAmtOther(unbilledRec.getWholesalePeakAirCharge());
                report.setPeakDollarAmt(0d);
            }
            report.setOffpeakDollarAmt(unbilledRec.getWholesaleOffpeakAirCharge());

            if (unbilledRec.getSource().equals("D")) {
                DataEvent dataEvent = this.getDataEventFromDb(this.tmpProdId);

                if (dataEvent.getDataeventsubtype().equals("DEFLT")) {
                    report.setDollarAmt3G(this.tmpChargeAmt);
                    report.setUsage3G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
                } else if (dataEvent.getDataeventsubtype().equals("DEF4G")) {
                    report.setDollarAmt4G(this.tmpChargeAmt);
                    report.setUsage4G(Math.round(unbilledRec.getTotalWholesaleUsage().doubleValue() / 1024));
                }
            }
            outRec.addWholesaleReportRecord(report);
            this.makeBookings(unbilledRec, outRec, tmpInterExchangeCarrierCode);
            return outRec;
        } else {
            this.processingHelper.incrementCounter(Constants.ZERO_CHARGES);
            return null;
        }
    }

    private WholesaleProcessingOutput processAdminFeesRecord(AdminFeeCsvFileDTO adminFeesRec) {
        WholesaleProcessingOutput outRec = new WholesaleProcessingOutput();
        AggregateWholesaleReportDTO report = this.processingHelper.addWholesaleReport();
        report.setBilledInd("Y");
        this.fileSource = "M";
        this.searchHomeSbid = adminFeesRec.getSbid();
        this.tmpProdId = adminFeesRec.getProductId();
        this.financialMarket = adminFeesRec.getFinancialMarket();

        WholesalePrice wholesalePrice = this.getWholesalePriceFromDb(this.tmpProdId, this.searchHomeSbid);

        this.tmpChargeAmt = wholesalePrice.getProductwholesaleprice().multiply(BigDecimal.valueOf(adminFeesRec.getAdminCount())).floatValue();
        report.setDollarAmtOther(this.tmpChargeAmt);
        outRec.addWholesaleReportRecord(report);
        this.makeBookings(adminFeesRec, outRec, tmpInterExchangeCarrierCode);
        return outRec;
    }

    protected FinancialMarket getFinancialMarketFromDb(String financialMarketId) {
        FinancialMarket result = null;
        List<FinancialMarket> dbResult = queryManager.getFinancialMarketRecords(financialMarketId);
        switch (dbResult.size()) {
            case 0:
                LOGGER.error(Constants.DB_NO_RESULT);
                break;
            case 1:
                for (FinancialMarket doc : dbResult) {
                    result = doc;
                }   
                break;
            default:
                LOGGER.error(Constants.DB_TOO_MANY_RECORDS);
                break;
        }
        return result;
    }

    protected FinancialEventCategory getEventCategoryFromDb(Integer tmpProdId, String homeEqualsServingSbid,
            boolean altBookingInd, int interExchangeCarrierCode, String financialeventnormalsign) {
        
        FinancialEventCategory result = null;
        List<FinancialEventCategory> dbResult = queryManager.getFinancialEventCategoryRecords(
                    tmpProdId, homeEqualsServingSbid, altBookingInd ? "Y" : "N", interExchangeCarrierCode, financialeventnormalsign);
                
        if (dbResult == null && financialeventnormalsign.equals("DR")) {
            LOGGER.warn(Constants.FEC_NOT_FOUND_MESSAGE);
            if (this.fileSource.equals("M")) {  // for admin fees call 0 product and 1 as inter exchange cde
                tmpProdId = 0;
                interExchangeCarrierCode = 1;
            } else {
                tmpProdId = 36;                 // for the rest 2 files call 36 product
            }
            dbResult = queryManager.getFinancialEventCategoryRecords(
                        tmpProdId, homeEqualsServingSbid, altBookingInd ? "Y" : "N", interExchangeCarrierCode, financialeventnormalsign);
            
            switch (dbResult.size()) {
            case 0:
                LOGGER.error(Constants.DEFAULT_FEC_NOT_FOUND);
                break;
            case 1:
                for (FinancialEventCategory doc : dbResult) {
                    result = doc;
                }   
                break;
            default:
                LOGGER.error(Constants.DB_TOO_MANY_RECORDS);
                break;
            }           
        }
        
        return result;
    }

    protected DataEvent getDataEventFromDb(Integer productId) {
        DataEvent result = null;
        List<DataEvent> dbResult = queryManager.getDataEventRecords(productId);
        switch (dbResult.size()) {
            case 0:
                LOGGER.error(Constants.DB_NO_RESULT);
                break;
            case 1:
                for (DataEvent doc : dbResult) {
                    result = doc;
                }   
                break;
            default:
                LOGGER.error(Constants.DB_TOO_MANY_RECORDS);
                break;
        }
        return result;
    }

    protected WholesalePrice getWholesalePriceFromDb(Integer tmpProdId, String searchHomeSbid) {
        WholesalePrice result = null;
        List<WholesalePrice> dbResult = queryManager.getWholesalePriceRecords(tmpProdId, searchHomeSbid);
        switch (dbResult.size()) {
            case 0:
                LOGGER.error(Constants.DB_NO_RESULT);
                break;
            case 1:
                for (WholesalePrice doc : dbResult) {
                    result = doc;
                }   
                break;
            default:
                LOGGER.error(Constants.DB_TOO_MANY_RECORDS);
                break;
        }
        return result;
    }
}