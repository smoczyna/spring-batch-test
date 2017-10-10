/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.FinancialEventOffsetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetReader extends CsvFileGenericReader<FinancialEventOffsetDTO> {
    private static final String[] COLUMN_NAMES = new String[] {"financialEvent", "offsetFinancialCategory"};
    
    @Autowired
    public FinancialEventOffsetReader(Environment environment, String filename) {
        super(FinancialEventOffsetDTO.class, environment, filename, COLUMN_NAMES, ",");
    }
    
    public FinancialEventOffsetReader(String filePath, String delimiter) {
        super(FinancialEventOffsetDTO.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
