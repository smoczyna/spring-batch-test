/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class UnbilledBookingFileReader extends CsvFileGenericReader<UnbilledCsvFileDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
        "homeSbid",
        "servingSbid",
        "messageSource",
        "airProdId",
        "wholesalePeakAirCharge",
        "wholesaleOffpeakAirCharge",
        "source",
        "financialMarket",
        "airBillSeconds",
        "totalWholesaleUsage"};
    
    @Autowired
    public UnbilledBookingFileReader(String filePath) {
        super(UnbilledCsvFileDTO.class, filePath, COLUMN_NAMES, ",", 0);
    }
    
    public UnbilledBookingFileReader(String filePath, String delimiter) {
        super(UnbilledCsvFileDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
