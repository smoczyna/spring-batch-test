/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.FinancialEventOffset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetRader extends CsvFileGenericReader<FinancialEventOffset> {
    private static final String[] COLUMN_NAMES = new String[] {"financialEvent", "offsetFinancialCategory"};
    
    @Autowired
    public FinancialEventOffsetRader(Environment environment, String filename) {
        super(FinancialEventOffset.class, environment, filename, COLUMN_NAMES, ",");
    }
    
    public FinancialEventOffsetRader(String filePath, String delimiter) {
        super(FinancialEventOffset.class, filePath, COLUMN_NAMES, delimiter, 0);
    }
}
