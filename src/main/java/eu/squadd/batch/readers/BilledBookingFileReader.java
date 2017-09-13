/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 *
 * @author smorcja
 */
public class BilledBookingFileReader implements ItemReader<BilledCsvFileDTO> {

    @Override
    public BilledCsvFileDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
