/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.writers;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author smoczyna
 */
public class CustomerCsvFileWriter extends FlatFileItemWriter<CustomerDTO> {
    
    private static final String PROPERTY_CSV_EXPORT_FILE_HEADER = "database.to.csv.job.export.file.header";
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    public CustomerCsvFileWriter(Environment environment) {
        
        String exportFileHeader = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_HEADER);
        this.setHeaderCallback(new StringHeaderWriter(exportFileHeader));

        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("customer.csv");
        this.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<CustomerDTO> lineAggregator = createCustomerLineAggregator();
        this.setLineAggregator(lineAggregator);
    }
    
    private LineAggregator<CustomerDTO> createCustomerLineAggregator() {
        DelimitedLineAggregator<CustomerDTO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<CustomerDTO> fieldExtractor = createCustomerFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }
    
    private FieldExtractor<CustomerDTO> createCustomerFieldExtractor() {
        BeanWrapperFieldExtractor<CustomerDTO> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"customerId", "discountCode", "zip", "name", "email"});
        return extractor;
    }
}
