/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.CustomerDTO;
import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 *
 * @author smoczyna
 */
public class CustomerDbReader extends JdbcCursorItemReader<CustomerDTO> {
    
    private static final String PROPERTY_CSV_EXPORT_QUERY = "database.to.csv.job.export.query";
    
    public CustomerDbReader(Environment environment, DataSource dataSource) {
        super();
        this.setDataSource(dataSource);
        this.setRowMapper(new BeanPropertyRowMapper<>(CustomerDTO.class));
        this.setSql(environment.getRequiredProperty(PROPERTY_CSV_EXPORT_QUERY));
    }
}
