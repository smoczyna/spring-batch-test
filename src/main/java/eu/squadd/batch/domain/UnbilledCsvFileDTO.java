/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 * Unbilled Bookings source payload class. This class represents the input file for billed bookings file: cmdunld.csv
 * @author smorcja
 */
public class UnbilledCsvFileDTO {
    private String homeSbid;
    private String servingSbid;
    private String messageSource;
    private Integer airProdId;
    private Double wholesaleAirChargePeak;
    private Double wholesaleAirChargeOffPeak;
    private String source;
    private String financialMarket;
    private Integer airBillSec;
    private Long totalWholesaleUsage;

    public String getHomeSbid() {
        return homeSbid;
    }

    public void setHomeSbid(String homeSbid) {
        this.homeSbid = homeSbid;
    }

    public String getServingSbid() {
        return servingSbid;
    }

    public void setServingSbid(String servingSbid) {
        this.servingSbid = servingSbid;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    public Integer getAirProdId() {
        return airProdId;
    }

    public void setAirProdId(Integer airProdId) {
        this.airProdId = airProdId;
    }

    public Double getWholesaleAirChargePeak() {
        return wholesaleAirChargePeak;
    }

    public void setWholesaleAirChargePeak(Double wholesaleAirChargePeak) {
        this.wholesaleAirChargePeak = wholesaleAirChargePeak;
    }

    public Double getWholesaleAirChargeOffPeak() {
        return wholesaleAirChargeOffPeak;
    }

    public void setWholesaleAirChargeOffPeak(Double wholesaleAirChargeOffPeak) {
        this.wholesaleAirChargeOffPeak = wholesaleAirChargeOffPeak;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFinancialMarket() {
        return financialMarket;
    }

    public void setFinancialMarket(String financialMarket) {
        this.financialMarket = financialMarket;
    }

    public Integer getAirBillSec() {
        return airBillSec;
    }

    public void setAirBillSec(Integer airBillSec) {
        this.airBillSec = airBillSec;
    }

    public Long getTotalWholesaleUsage() {
        return totalWholesaleUsage;
    }

    public void setTotalWholesaleUsage(Long totalWholesaleUsage) {
        this.totalWholesaleUsage = totalWholesaleUsage;
    }
}
