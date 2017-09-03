/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.writers;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author smoczyna
 */
public class CustomerDbWriter extends JdbcBatchItemWriter<CustomerDTO> {
    
    private static final String QUERY_INSERT_CUSTOMER = "INSERT " +
            "INTO customer(customer_id, discount_code, zip, name, email) " +
            "VALUES (:customerId, :discountCode, :zip, :name, :email)";
    
    public CustomerDbWriter(DataSource dataSource) {
        super();
        this.setDataSource(dataSource);
        this.setJdbcTemplate(new NamedParameterJdbcTemplate(dataSource));
        this.setSql(QUERY_INSERT_CUSTOMER);
        ItemSqlParameterSourceProvider<CustomerDTO> sqlParameterSourceProvider = new BeanPropertyItemSqlParameterSourceProvider();
        setItemSqlParameterSourceProvider(sqlParameterSourceProvider);
    }
}
