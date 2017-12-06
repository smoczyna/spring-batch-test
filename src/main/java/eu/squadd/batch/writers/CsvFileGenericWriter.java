/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class CsvFileGenericWriter<T> extends FlatFileItemWriter<T> {
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "destination.files.path";
    
    public CsvFileGenericWriter(String fileName, String[] fieldNames, String delimiter) {
        super.setAppendAllowed(true);
        this.setResource(new FileSystemResource(fileName));
        this.setLineAggregator(createLineAggregator(fieldNames, delimiter));
    }
    
    public CsvFileGenericWriter(Environment environment, String fileName, String[] fieldNames, String delimiter) {
        this(environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat(fileName), fieldNames, delimiter);
    }
    
    private LineAggregator<T> createLineAggregator(String[] fieldNames, String delimiter) {
        DelimitedLineAggregator<T> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(delimiter);
        lineAggregator.setFieldExtractor(createFieldExtractor(fieldNames));
        return lineAggregator;
    }
    
    private FieldExtractor<T> createFieldExtractor(String[] fieldNames) {
        BeanWrapperFieldExtractor<T> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(fieldNames);
        return extractor;
    }
}