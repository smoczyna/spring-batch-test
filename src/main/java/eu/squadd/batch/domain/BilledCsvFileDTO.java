/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 * Billed Bookings source payload class. This class represents the input file for billed bookings file: bmdunld.csv
 * @author smorcja
 */
public class BilledCsvFileDTO extends BaseBookingDTO {

    private String incompleteInd;
    private Integer incompleteProdId;
    private Double incompleteCallSurcharge;
    private Integer airSurchargeProductId;
    private Double airSurcharge;
    private Integer interExchangeCarrierCode;
    private Integer tollProductId;
    private Double tollCharge;
    private Integer tollSurchargeProductId;
    private Double tollSurcharge;
    private Double tollStateTax;
    private Double tollLocalTax;
    private Double localAirTax;
    private Double stateAirTax;
    private Double wholesaleTollChargeLDPeak;
    private Double wholesaleTollChargeLDOther;                   
    private String space;
    private String deviceType;
    private Integer tollBillSeconds;
    private Long wholesaleUsageBytes;

    
    public String getIncompleteInd() {
        return incompleteInd;
    }

    public void setIncompleteInd(String incompleteInd) {
        this.incompleteInd = incompleteInd;
    }

    public Integer getIncompleteProdId() {
        return incompleteProdId;
    }

    public void setIncompleteProdId(Integer incompleteProdId) {
        this.incompleteProdId = incompleteProdId;
    }

    public Double getIncompleteCallSurcharge() {
        return incompleteCallSurcharge;
    }

    public void setIncompleteCallSurcharge(Double incompleteCallSurcharge) {
        this.incompleteCallSurcharge = incompleteCallSurcharge;
    }

    public Integer getAirSurchargeProductId() {
        return airSurchargeProductId;
    }

    public void setAirSurchargeProductId(Integer airSurchargeProductId) {
        this.airSurchargeProductId = airSurchargeProductId;
    }

    public Double getAirSurcharge() {
        return airSurcharge;
    }

    public void setAirSurcharge(Double airSurcharge) {
        this.airSurcharge = airSurcharge;
    }

    public Integer getInterExchangeCarrierCode() {
        return interExchangeCarrierCode;
    }

    public void setInterExchangeCarrierCode(Integer interExchangeCarrierCode) {
        this.interExchangeCarrierCode = interExchangeCarrierCode;
    }

    public Integer getTollProductId() {
        return tollProductId;
    }

    public void setTollProductId(Integer tollProductId) {
        this.tollProductId = tollProductId;
    }

    public Double getTollCharge() {
        return tollCharge;
    }

    public void setTollCharge(Double tollCharge) {
        this.tollCharge = tollCharge;
    }

    public Integer getTollSurchargeProductId() {
        return tollSurchargeProductId;
    }

    public void setTollSurchargeProductId(Integer tollSurchargeProductId) {
        this.tollSurchargeProductId = tollSurchargeProductId;
    }

    public Double getTollSurcharge() {
        return tollSurcharge;
    }

    public void setTollSurcharge(Double tollSurcharge) {
        this.tollSurcharge = tollSurcharge;
    }

    public Double getTollStateTax() {
        return tollStateTax;
    }

    public void setTollStateTax(Double tollStateTax) {
        this.tollStateTax = tollStateTax;
    }

    public Double getTollLocalTax() {
        return tollLocalTax;
    }

    public void setTollLocalTax(Double tollLocalTax) {
        this.tollLocalTax = tollLocalTax;
    }

    public Double getLocalAirTax() {
        return localAirTax;
    }

    public void setLocalAirTax(Double localAirTax) {
        this.localAirTax = localAirTax;
    }

    public Double getStateAirTax() {
        return stateAirTax;
    }

    public void setStateAirTax(Double stateAirTax) {
        this.stateAirTax = stateAirTax;
    }

    public Double getWholesaleTollChargeLDPeak() {
        return wholesaleTollChargeLDPeak;
    }

    public void setWholesaleTollChargeLDPeak(Double wholesaleTollChargeLDPeak) {
        this.wholesaleTollChargeLDPeak = wholesaleTollChargeLDPeak;
    }

    public Double getWholesaleTollChargeLDOther() {
        return wholesaleTollChargeLDOther;
    }

    public void setWholesaleTollChargeLDOther(Double wholesaleTollChargeLDOther) {
        this.wholesaleTollChargeLDOther = wholesaleTollChargeLDOther;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getTollBillSeconds() {
        return tollBillSeconds;
    }

    public void setTollBillSeconds(Integer tollBillSeconds) {
        this.tollBillSeconds = tollBillSeconds;
    }

    public Long getWholesaleUsageBytes() {
        return wholesaleUsageBytes;
    }

    public void setWholesaleUsageBytes(Long wholesaleUsageBytes) {
        this.wholesaleUsageBytes = wholesaleUsageBytes;
    }

    @Override
    public String toString() {
        return "BilledCsvFileDTO{" + "homeSbid=" + getHomeSbid() 
                + ", servingSbid=" + getServingSbid() 
                + ", messageSource=" + getMessageSource()
                + ", incompleteInd=" + incompleteInd 
                + ", airProdId=" + getAirProdId() 
                + ", incompleteProdId=" + incompleteProdId 
                + ", incompleteCallSurcharge=" + incompleteCallSurcharge 
                + ", airSurchargeProductId=" + airSurchargeProductId 
                + ", airSurcharge=" + airSurcharge 
                + ", interExchangeCarrierCode=" + interExchangeCarrierCode 
                + ", tollProductId=" + tollProductId 
                + ", tollCharge=" + tollCharge 
                + ", tollSurchargeProductId=" + tollSurchargeProductId 
                + ", tollSurcharge=" + tollSurcharge 
                + ", tollStateTax=" + tollStateTax 
                + ", tollLocalTax=" + tollLocalTax 
                + ", localAirTax=" + localAirTax 
                + ", stateAirTax=" + stateAirTax 
                + ", wholesalePeakAirCharge=" + getWholesaleAirChargePeak() 
                + ", wholesaleOffPeakAirCharge=" + getWholesaleAirChargeOffPeak() 
                + ", wholesaleTollChargeLDPeak=" + wholesaleTollChargeLDPeak 
                + ", wholesaleTollChargeLDOther=" + wholesaleTollChargeLDOther 
                + ", space=" + space
                + ", financialMarket=" + getFinancialMarket() 
                + ", deviceType=" + deviceType 
                + ", airBillSeconds=" + getAirBillSeconds() 
                + ", tollBillSeconds=" + tollBillSeconds 
                + ", wholesaleUsageBytes=" + wholesaleUsageBytes + '}';
    }
}
