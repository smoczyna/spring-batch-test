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
public interface BaseBookingInputInterface extends MinBookingInterface {
    String getHomeSbid();
    String getServingSbid();
    String getMessageSource();
    Integer getAirProdId();
    Double getWholesalePeakAirCharge();
    Double getWholesaleOffpeakAirCharge();
    String getFinancialMarket();
    Integer getAirBillSeconds();    
}
