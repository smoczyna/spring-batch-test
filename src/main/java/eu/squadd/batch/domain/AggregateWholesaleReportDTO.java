/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 * Aggregate Wholesale Report DTO payload class. 
 * This class represents the output file of the application called aggregate wholesale report
 * @author smorcja
 */
public class AggregateWholesaleReportDTO {
    
    private String cycleMonthYear;
    private String startDate;
    private String endDate;
    private String homeLegalEntity;
    private String servingLegalEntity;
    private String homeFinancialMarketId;
    private String servingFinancialMarketId;
    private Integer productDiscountOfferId;
    private Short contractTermId;
    private Double peakDollarAmt;
    private Double offpeakDollarAmt;
    private Integer voiceMinutes;
    private Double tollDollarsAmt;
    private Integer tollMinutes;
    private Double dollarAmt3G;
    private Long usage3G;
    private Double dollarAmt4G;
    private Long usage4G;
    private Double dollarAmtOther;
    private String dbCrInd;
    private String billedInd;

    public String getCycleMonthYear() {
        return cycleMonthYear;
    }

    public void setCycleMonthYear(String cycleMonthYear) {
        this.cycleMonthYear = cycleMonthYear;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHomeLegalEntity() {
        return homeLegalEntity;
    }

    public void setHomeLegalEntity(String homeLegalEntity) {
        this.homeLegalEntity = homeLegalEntity;
    }

    public String getServingLegalEntity() {
        return servingLegalEntity;
    }

    public void setServingLegalEntity(String servingLegalEntity) {
        this.servingLegalEntity = servingLegalEntity;
    }

    public String getHomeFinancialMarketId() {
        return homeFinancialMarketId;
    }

    public void setHomeFinancialMarketId(String homeFinancialMarketId) {
        this.homeFinancialMarketId = homeFinancialMarketId;
    }

    public String getServingFinancialMarketId() {
        return servingFinancialMarketId;
    }

    public void setServingFinancialMarketId(String servingFinancialMarketId) {
        this.servingFinancialMarketId = servingFinancialMarketId;
    }

    public Integer getProductDiscountOfferId() {
        return productDiscountOfferId;
    }

    public void setProductDiscountOfferId(Integer productDiscountOfferId) {
        this.productDiscountOfferId = productDiscountOfferId;
    }

    public Short getContractTermId() {
        return contractTermId;
    }

    public void setContractTermId(Short contractTermId) {
        this.contractTermId = contractTermId;
    }

    public Double getPeakDollarAmt() {
        return peakDollarAmt;
    }

    public void setPeakDollarAmt(Double peakDollarAmt) {
        this.peakDollarAmt = peakDollarAmt;
    }
   
    public Double getOffpeakDollarAmt() {
        return offpeakDollarAmt;
    }

    public void setOffpeakDollarAmt(Double offpeakDollarAmt) {
        this.offpeakDollarAmt = offpeakDollarAmt;
    }

    public Integer getVoiceMinutes() {
        return voiceMinutes;
    }

    public void setVoiceMinutes(Integer voiceMinutes) {
        this.voiceMinutes = voiceMinutes;
    }

    public Double getTollDollarsAmt() {
        return tollDollarsAmt;
    }

    public void setTollDollarsAmt(Double tollDollarsAmt) {
        this.tollDollarsAmt = tollDollarsAmt;
    }

    public Integer getTollMinutes() {
        return tollMinutes;
    }

    public void setTollMinutes(Integer tollMinutes) {
        this.tollMinutes = tollMinutes;
    }

    public Double getDollarAmt3G() {
        return dollarAmt3G;
    }

    public void setDollarAmt3G(Double dollarAmt3G) {
        this.dollarAmt3G = dollarAmt3G;
    }

    public Long getUsage3G() {
        return usage3G;
    }

    public void setUsage3G(Long usage3G) {
        this.usage3G = usage3G;
    }

    public Double getDollarAmt4G() {
        return dollarAmt4G;
    }

    public void setDollarAmt4G(Double dollarAmt4G) {
        this.dollarAmt4G = dollarAmt4G;
    }

    public Long getUsage4G() {
        return usage4G;
    }

    public void setUsage4G(Long usage4G) {
        this.usage4G = usage4G;
    }

    public Double getDollarAmtOther() {
        return dollarAmtOther;
    }

    public void setDollarAmtOther(Double dollarAmtOther) {
        this.dollarAmtOther = dollarAmtOther;
    }

    public String getDbCrInd() {
        return dbCrInd;
    }

    public void setDbCrInd(String dbCrInd) {
        this.dbCrInd = dbCrInd;
    }

    public String getBilledInd() {
        return billedInd;
    }

    public void setBilledInd(String billedInd) {
        this.billedInd = billedInd;
    }
}
