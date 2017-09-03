/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.prototypes;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import java.util.List;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcPagingItemReader;

/**
 *
 * @author smorcja
 */
public class CustomItemReader extends JdbcPagingItemReader<CustomerDTO> {
    
    private List<CustomerDTO> recordList;
    private int recordCount = 0;
    //private int pageSize;
    private int nextCustomerIndex;

//    public CustomItemReader(int pageSize) {
//        this.pageSize = pageSize;
//    }
    
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
