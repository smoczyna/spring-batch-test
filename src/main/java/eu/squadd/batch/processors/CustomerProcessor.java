/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.processors;

import eu.squadd.batch.domain.CustomerDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author smorcja
 */
public class CustomerProcessor implements ItemProcessor<CustomerDTO, SummarySubLedgerDTO> {

    @Override
    public SummarySubLedgerDTO process(CustomerDTO item) throws Exception {
        SummarySubLedgerDTO ssld = new SummarySubLedgerDTO();
        ssld.setFinancialEventNo(1);
        ssld.setFinancialCategory(1);
        ssld.setFinancialMarketId("dublin");
        
        return ssld;
    }
}
