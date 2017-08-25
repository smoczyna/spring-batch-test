/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.util;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class CustomerIdGenerator {
    
    private static final AtomicInteger ID_COUNT = new AtomicInteger(1000);  
    
    public Integer generateId() {
        //System.out.println("Current Generated No: "+ID_COUNT);
        return ID_COUNT.incrementAndGet(); 
    }
}
