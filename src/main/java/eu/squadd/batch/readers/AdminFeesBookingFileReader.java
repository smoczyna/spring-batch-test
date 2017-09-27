/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class AdminFeesBookingFileReader extends CsvFileGenericReader<AdminFeeCsvFileDTO> {
    private static final String[] COLUMN_NAMES = new String[] {
        "sbid",
        "productId",
        "adminChargeAmt",
        "adminCount",
        "financialMarket"};
    
    @Autowired
    public AdminFeesBookingFileReader(Environment environment, String filename) {
        super(AdminFeeCsvFileDTO.class, environment, filename, COLUMN_NAMES, ",");
    }
    
    public AdminFeesBookingFileReader(String filePath, String delimiter) {
        super(AdminFeeCsvFileDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
