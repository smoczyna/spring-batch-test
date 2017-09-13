/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author smorcja
 */
public class AdminFeeFileProcessor implements ItemProcessor<AdminFeeCsvFileDTO, SummarySubLedgerDTO> {

    @Override
    public SummarySubLedgerDTO process(AdminFeeCsvFileDTO i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
