/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.SummarySubLedgerPK;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class BookDateProcessor implements ItemProcessor<BookDateCsvFileDTO, Map<SummarySubLedgerPK, SummarySubLedgerDTO>> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookDateProcessor.class);
    
    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    @Override
    public Map process(BookDateCsvFileDTO rec) throws Exception {
        LOGGER.info("Booking Date Processor - udating sub ledger records with booking dates");
        this.tempSubLedgerOuput.updateBookingDates(rec);
        return this.tempSubLedgerOuput.getAggregatedOutput();
    }
}
