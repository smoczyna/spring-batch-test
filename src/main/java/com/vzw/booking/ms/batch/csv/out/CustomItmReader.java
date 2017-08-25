/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.out;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 *
 * @author smorcja
 */
public class CustomItmReader implements ItemReader<CustomerDTO> {
    
    private List<CustomerDTO> recordList;
    private int recordCount = 0;

    @Override
    public CustomerDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (recordCount < recordList.size()) {
            return recordList.get(recordCount++);
        } else {
            return null;
        }
    }

    public List<CustomerDTO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CustomerDTO> recordList) {
        this.recordList = recordList;
    }
}
