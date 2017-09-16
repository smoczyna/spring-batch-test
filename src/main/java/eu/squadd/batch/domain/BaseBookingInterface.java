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
public interface BaseBookingInterface {
    String getHomeSbid();
    String getServingSbid();
    String getMessageSource();
    Integer getAirProdId();
    Double getWholesaleAirChargePeak();
    Double getWholesaleAirChargeOffPeak();
    String getSource();
    String getFinancialMarket();
    Integer getAirBillSeconds();    
}
