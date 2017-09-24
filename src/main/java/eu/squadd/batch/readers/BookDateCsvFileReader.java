/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class BookDateCsvFileReader extends CsvFileGenericReader<BookDateCsvFileDTO> {    
    
    private static final String[] COLUMN_NAMES = new String[]{"rptPerStartDate", "rptPerEndDate", "transPerStartDate", "transPerEndDate", "monthEndCycle"};
    
    public BookDateCsvFileReader(Environment environment, String filename) {
        super(BookDateCsvFileDTO.class, environment, filename, COLUMN_NAMES, ",");
    }
    
    public BookDateCsvFileReader(String filPath) {
        super(BookDateCsvFileDTO.class, filPath, COLUMN_NAMES, ",", 0);
    }
}
