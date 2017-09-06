/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.processors;

import com.vzw.booking.ms.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.ms.batch.domain.UnbilledCsvFileDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author smorcja
 */
public class UnbilledFileProcessor implements ItemProcessor<UnbilledCsvFileDTO, SummarySubLedgerDTO> {
    
    @Override
    public SummarySubLedgerDTO process(UnbilledCsvFileDTO i) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
