/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class BilledFileProcessor implements ItemProcessor<BilledCsvFileDTO, SummarySubLedgerDTO> {
    
    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    @Override
    public SummarySubLedgerDTO process(BilledCsvFileDTO record) throws Exception {
        return this.tempSubLedgerOuput.add(record);
    }
}
