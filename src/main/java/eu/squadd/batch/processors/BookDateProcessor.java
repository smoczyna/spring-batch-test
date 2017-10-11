/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class BookDateProcessor implements ItemProcessor<BookDateCsvFileDTO, Boolean> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookDateProcessor.class);
    
    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    @Override
    public Boolean process(BookDateCsvFileDTO dates) throws Exception {
        this.tempSubLedgerOuput.setDates(dates);
        return true; //this.tempSubLedgerOuput.getAggregatedOutput();
    }
}
