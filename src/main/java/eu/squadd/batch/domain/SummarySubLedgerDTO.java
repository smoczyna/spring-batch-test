/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 * Summary Sub Ledger internal payload class. 
 * This class represents the output file of the application.
 * @author smorcja
 */
public class SummarySubLedgerDTO extends SummarySubLedgerPK {
    
    private String jemsApplId = "BL";
    private String reportStartDate;
    private String jemsApplTransactioDate;
    private Integer subledgerSequenceNo;
    private Double subledgerTotalDebitAmount;
    private Double subledgerTotalCreditAmount;
    private String jurnalEventNo;
    private String jurnalEventExceptionCode;
    private String jurnalEventReadInd;
    private Integer jurnalLedgerTransactionNo;
    private String billCycleNo = "00";
    private String billTypeCode = "C ";
    private String billCycleMonthYear;
    private String billPhaseType = "J6";
    private String billMonthInd;
    private String billAccrualIndicator = "N";
    private String paymentSourceCode;
    private Integer discountOfferId;
    private String updateuserId;
    private String updateTimestamp;

    public SummarySubLedgerDTO(SummarySubLedgerPK pk) {
        super(pk.getFinancialEventNo(), pk.getFinancialCategory(), pk.getFinancialMarketId());
    }
    
    public SummarySubLedgerDTO(Integer financialEventNo, Integer financialCategory, String financialMarketId) {
        super(financialEventNo, financialCategory, financialMarketId);
    }
    
    public String getJemsApplId() {
        return jemsApplId;
    }

    public void setJemsApplId(String jemsApplId) {
        this.jemsApplId = jemsApplId;
    }

    public String getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(String reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public String getJemsApplTransactioDate() {
        return jemsApplTransactioDate;
    }

    public void setJemsApplTransactioDate(String jemsApplTransactioDate) {
        this.jemsApplTransactioDate = jemsApplTransactioDate;
    }

    public Integer getSubledgerSequenceNo() {
        return subledgerSequenceNo;
    }

    public void setSubledgerSequenceNo(Integer subledgerSequenceNo) {
        this.subledgerSequenceNo = subledgerSequenceNo;
    }

    public Double getSubledgerTotalDebitAmount() {
        return subledgerTotalDebitAmount;
    }

    public void setSubledgerTotalDebitAmount(Double subledgerTotalDebitAmount) {
        this.subledgerTotalDebitAmount = subledgerTotalDebitAmount;
    }

    public Double getSubledgerTotalCreditAmount() {
        return subledgerTotalCreditAmount;
    }

    public void setSubledgerTotalCreditAmount(Double subledgerTotalCreditAmount) {
        this.subledgerTotalCreditAmount = subledgerTotalCreditAmount;
    }

    public String getJurnalEventNo() {
        return jurnalEventNo;
    }

    public void setJurnalEventNo(String jurnalEventNo) {
        this.jurnalEventNo = jurnalEventNo;
    }

    public String getJurnalEventExceptionCode() {
        return jurnalEventExceptionCode;
    }

    public void setJurnalEventExceptionCode(String jurnalEventExceptionCode) {
        this.jurnalEventExceptionCode = jurnalEventExceptionCode;
    }

    public String getJurnalEventReadInd() {
        return jurnalEventReadInd;
    }

    public void setJurnalEventReadInd(String jurnalEventReadInd) {
        this.jurnalEventReadInd = jurnalEventReadInd;
    }

    public Integer getJurnalLedgerTransactionNo() {
        return jurnalLedgerTransactionNo;
    }

    public void setJurnalLedgerTransactionNo(Integer jurnalLedgerTransactionNo) {
        this.jurnalLedgerTransactionNo = jurnalLedgerTransactionNo;
    }

    public String getBillCycleNo() {
        return billCycleNo;
    }

    public void setBillCycleNo(String billCycleNo) {
        this.billCycleNo = billCycleNo;
    }

    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    public String getBillCycleMonthYear() {
        return billCycleMonthYear;
    }

    public void setBillCycleMonthYear(String billCycleMonthYear) {
        this.billCycleMonthYear = billCycleMonthYear;
    }

    public String getBillPhaseType() {
        return billPhaseType;
    }

    public void setBillPhaseType(String billPhaseType) {
        this.billPhaseType = billPhaseType;
    }

    public String getBillMonthInd() {
        return billMonthInd;
    }

    public void setBillMonthInd(String billMonthInd) {
        this.billMonthInd = billMonthInd;
    }

    public String getBillAccrualIndicator() {
        return billAccrualIndicator;
    }

    public void setBillAccrualIndicator(String billAccrualIndicator) {
        this.billAccrualIndicator = billAccrualIndicator;
    }

    public String getPaymentSourceCode() {
        return paymentSourceCode;
    }

    public void setPaymentSourceCode(String paymentSourceCode) {
        this.paymentSourceCode = paymentSourceCode;
    }

    public Integer getDiscountOfferId() {
        return discountOfferId;
    }

    public void setDiscountOfferId(Integer discountOfferId) {
        this.discountOfferId = discountOfferId;
    }

    public String getUpdateuserId() {
        return updateuserId;
    }

    public void setUpdateuserId(String updateuserId) {
        this.updateuserId = updateuserId;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
