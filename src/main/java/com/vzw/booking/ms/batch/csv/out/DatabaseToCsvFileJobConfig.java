package com.vzw.booking.ms.batch.csv.out;

import com.vzw.booking.ms.batch.config.DerbyDbConfig;
import com.vzw.booking.ms.batch.csv.in.CsvFileToDatabaseJobConfig;
import com.vzw.booking.ms.batch.csv.processor.CustomerProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.vzw.booking.ms.batch.domain.CustomerDTO;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.batch.item.database.support.DerbyPagingQueryProvider;

/**
 * @author Petri Kainulainen
 */
@Configuration
public class DatabaseToCsvFileJobConfig {

    private static final String PROPERTY_CSV_EXPORT_FILE_HEADER = "database.to.csv.job.export.file.header";
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";

    @Bean
    ItemReader<CustomerDTO> databaseCsvItemReader() {
        JdbcPagingItemReader<CustomerDTO> databaseReader = new JdbcPagingItemReader<>();
        try {
            databaseReader.setDataSource(DerbyDbConfig.getBasicDS("APP", "APP"));            
        } catch (SQLException ex) {
            Logger.getLogger(CsvFileToDatabaseJobConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        databaseReader.setPageSize(1);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(CustomerDTO.class));

        PagingQueryProvider queryProvider = createQueryProvider();
        databaseReader.setQueryProvider(queryProvider);

        return databaseReader;
    }

    private PagingQueryProvider createQueryProvider() {
        //H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
        DerbyPagingQueryProvider queryProvider = new DerbyPagingQueryProvider();
        queryProvider.setSelectClause("SELECT customer_id, discount_code, zip, name, email");
        queryProvider.setFromClause("FROM customer");
        queryProvider.setSortKeys(sortByCustomerIdAsc());

        return queryProvider;
    }

    private Map<String, Order> sortByCustomerIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("customer_id", Order.ASCENDING);
        return sortConfiguration;
    }

    @Bean
    ItemProcessor<CustomerDTO, CustomerDTO> databaseCsvItemProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    ItemWriter<CustomerDTO> databaseCsvItemWriter(Environment environment) {
        FlatFileItemWriter<CustomerDTO> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_HEADER);
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH);
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<CustomerDTO> lineAggregator = createCustomerLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
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

    @Bean
    Step databaseToCsvFileStep(ItemReader<CustomerDTO> databaseCsvItemReader,
                               ItemProcessor<CustomerDTO, CustomerDTO> databaseCsvItemProcessor,
                               ItemWriter<CustomerDTO> databaseCsvItemWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("databaseToCsvFileStep")
                .<CustomerDTO, CustomerDTO>chunk(1)
                .reader(databaseCsvItemReader)
                .processor(databaseCsvItemProcessor)
                .writer(databaseCsvItemWriter)
                .build();
    }

    @Bean
    Job databaseToCsvFileJob(JobBuilderFactory jobBuilderFactory,
                             @Qualifier("databaseToCsvFileStep") Step csvStudentStep) {
        return jobBuilderFactory.get("databaseToCsvFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(csvStudentStep)
                .end()
                .build();
    }
}
