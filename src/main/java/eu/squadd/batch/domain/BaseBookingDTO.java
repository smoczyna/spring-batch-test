/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 *
 * @author smoczyna
 */
public class BaseBookingDTO implements BaseBookingInputRecord {
    private String homeSbid;
    private String servingSbid;
    private String messageSource;
    private Integer airProdId;
    private Double wholesaleAirChargePeak;
    private Double wholesaleAirChargeOffPeak;
    private String source;
    private String financialMarket;
    private Integer airBillSeconds;

    @Override
    public String getHomeSbid() {
        return homeSbid;
    }

    public void setHomeSbid(String homeSbid) {
        this.homeSbid = homeSbid;
    }

    @Override
    public String getServingSbid() {
        return servingSbid;
    }

    public void setServingSbid(String servingSbid) {
        this.servingSbid = servingSbid;
    }

    @Override
    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Integer getAirProdId() {
        return airProdId;
    }

    public void setAirProdId(Integer airProdId) {
        this.airProdId = airProdId;
    }

    @Override
    public Double getWholesaleAirChargePeak() {
        return wholesaleAirChargePeak;
    }

    public void setWholesaleAirChargePeak(Double wholesaleAirChargePeak) {
        this.wholesaleAirChargePeak = wholesaleAirChargePeak;
    }

    @Override
    public Double getWholesaleAirChargeOffPeak() {
        return wholesaleAirChargeOffPeak;
    }

    public void setWholesaleAirChargeOffPeak(Double wholesaleAirChargeOffPeak) {
        this.wholesaleAirChargeOffPeak = wholesaleAirChargeOffPeak;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getFinancialMarket() {
        return financialMarket;
    }

    public void setFinancialMarket(String financialMarket) {
        this.financialMarket = financialMarket;
    }

    @Override
    public Integer getAirBillSeconds() {
        return airBillSeconds;
    }

    public void setAirBillSeconds(Integer airBillSeconds) {
        this.airBillSeconds = airBillSeconds;
    }
    
    
}
