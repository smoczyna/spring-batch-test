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
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    public CsvFileGenericWriter(String fileName, String[] fieldNames) {
        super.setAppendAllowed(true);
        this.setResource(new FileSystemResource(fileName));
        LineAggregator<T> lineAggregator = createWholesaleReportLineAggregator(fieldNames);
        this.setLineAggregator(lineAggregator);
    }
    
    public CsvFileGenericWriter(Environment environment, String fileName, String[] fieldNames) {
        this(environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat(fileName), fieldNames);
    }
    
    private LineAggregator<T> createWholesaleReportLineAggregator(String[] fieldNames) {
        DelimitedLineAggregator<T> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<T> fieldExtractor = createWholesaleReportFieldExtractor(fieldNames);
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<T> createWholesaleReportFieldExtractor(String[] fieldNames) {
        BeanWrapperFieldExtractor<T> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(fieldNames);
        return extractor;
    }
}
