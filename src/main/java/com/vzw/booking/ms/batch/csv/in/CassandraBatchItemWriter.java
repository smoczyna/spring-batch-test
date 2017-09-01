/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.in;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.datastax.driver.core.Session;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class CassandraBatchItemWriter<T> implements ItemWriter<T>, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileReaderListener.class);
    private final Class<T> aClass;
    
    @Autowired
    private Session session;

    @Override
    public void afterPropertiesSet() throws Exception { }

    public CassandraBatchItemWriter(final Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public void write(List<? extends T> list) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}