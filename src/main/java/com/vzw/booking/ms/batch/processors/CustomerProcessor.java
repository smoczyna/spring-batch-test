/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.processors;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author smorcja
 */
public class CustomerProcessor implements ItemProcessor<CustomerDTO, CustomerDTO> {
    
//    private static final Logger LOGGER = LoggerFactory.getLogger(StudentProcessor.class);

    @Override
    public CustomerDTO process(CustomerDTO item) throws Exception {
        //this.prettyPrint(item);
        return item;
    }
    
//    private void prettyPrint(CustomerDTO item) {
//        LOGGER.info("*** Processing customer record: ***");
//        LOGGER.info("       customer ID: " + item.getCustomerId());
//        LOGGER.info("       customer name: " + item.getName());
//        LOGGER.info("       customer email: " + item.getEmail());
//        LOGGER.info("       customer zip code: " + item.getZip());
//        LOGGER.info("*** end of customer ***");
//    }
    
}
