/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 * Generic reader class for any flat file
 * it is used for all CSV files read from mainframe
 * @author smorcja
 * @param <T>
 */
public class CsvFileGenericReader<T> extends FlatFileItemReader<T> {
    private final Class<T> payloadClass;
    private static final String PROPERTY_CSV_SOURCE_FILE_PATH = "csv.to.database.job.source.file.path";
    
    public CsvFileGenericReader(Class<T> payloadClass, Environment environment, String filename, String[] fieldNames, String delimiter, int lineToSkip) {
        super();
        this.payloadClass = payloadClass;
        String importFilePath = environment.getRequiredProperty(PROPERTY_CSV_SOURCE_FILE_PATH).concat(filename);
        this.setResource(new FileSystemResource(importFilePath));
        this.setLinesToSkip(lineToSkip);
        this.setLineMapper(createLineMapper(fieldNames, delimiter));        
    }
    
    protected final LineMapper<T> createLineMapper(String[] fieldNames, String delimiter) {
        DefaultLineMapper<T> studentLineMapper = new DefaultLineMapper<>();
        if (fieldNames != null || fieldNames.length > 0) {
            LineTokenizer studentLineTokenizer = createLineTokenizer(fieldNames, delimiter);
            studentLineMapper.setLineTokenizer(studentLineTokenizer);
        }
        FieldSetMapper<T> studentInformationMapper = createInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);

        return studentLineMapper;
    }
    
    protected LineTokenizer createLineTokenizer(String[] fieldNames, String delimiter) {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(delimiter);
        studentLineTokenizer.setNames(fieldNames);
        return studentLineTokenizer;
    }

    protected FieldSetMapper<T> createInformationMapper() {
        BeanWrapperFieldSetMapper<T> studentInformationMapper = new BeanWrapperFieldSetMapper();
        studentInformationMapper.setTargetType(payloadClass);
        return studentInformationMapper;
    }
}
