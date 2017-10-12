/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.utils.WholesaleBookingProcessorHelper;
import eu.squadd.batch.domain.FinancialEventOffsetDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class FinancialEventOffsetProcessor implements ItemProcessor<FinancialEventOffsetDTO, Boolean> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialEventOffsetProcessor.class);
    
    @Autowired
    WholesaleBookingProcessorHelper tempSubLedgerOuput;
    
    @Override
    public Boolean process(FinancialEventOffsetDTO offset) throws Exception {
        return this.tempSubLedgerOuput.addOffset(offset);
    }
}
