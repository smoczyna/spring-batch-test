/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import com.vzw.booking.ms.batch.domain.BilledCsvFileDTO;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class BilledBookingFileReader extends CsvFileGenericReader<BilledCsvFileDTO> {
    
    public BilledBookingFileReader(Environment environment) {
        super(BilledCsvFileDTO.class, environment, "bmdunld.csv", new String[]{
            "homeSbid",
            "servingSbid",
            "messageSource",
            "incompleteInd",
            "airProdId",
            "incompleteProdId",
            "incompleteCallSurcharge",
            "airSurchargeProductId",
            "airSurcharge",
            "interExchangeCarrierCode",
            "tollProductId",
            "tollCharge",
            "tollSurchargeProductId",
            "tollSurcharge",
            "tollStateTax",
            "tollLocalTax",
            "localAirTax",
            "stateAirTax",
            "wholesalePeakAirCharge",
            "wholesaleOffPeakAirCharge",
            "wholesaleTollChargeLDPeak",
            "wholesaleTollChargeLDOther",
            "space",
            "financialMarket",
            "deviceType",
            "airBillSeconds",
            "tollBillSeconds",
            "wholesaleUsageBytes"},
        ";");
    }
}
