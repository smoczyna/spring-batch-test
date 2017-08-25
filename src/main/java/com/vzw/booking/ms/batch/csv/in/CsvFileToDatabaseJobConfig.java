package com.vzw.booking.ms.batch.csv.in;

import com.vzw.booking.ms.batch.config.DerbyDbConfig;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import com.vzw.booking.ms.batch.csv.processor.StudentProcessor;
import com.vzw.booking.ms.batch.domain.CustomerDTO;
import com.vzw.booking.ms.batch.domain.StudentDTO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * @author Petri Kainulainen
 */
@Configuration
public class CsvFileToDatabaseJobConfig {

    private static final String PROPERTY_CSV_SOURCE_FILE_PATH = "csv.to.database.job.source.file.path";
    private static final String QUERY_INSERT_CUSTOMER = "INSERT " +
            "INTO customer(customer_id, discount_code, zip, name, email) " +
            "VALUES (:customerId, :discountCode, :zip, :name, :email)";

    @Bean
    ItemReader<StudentDTO> csvFileItemReader(Environment environment) {
        FlatFileItemReader<StudentDTO> csvFileReader = new FlatFileItemReader<>();
        csvFileReader.setResource(new ClassPathResource(environment.getRequiredProperty(PROPERTY_CSV_SOURCE_FILE_PATH)));
        csvFileReader.setLinesToSkip(1);

        LineMapper<StudentDTO> studentLineMapper = createStudentLineMapper();
        csvFileReader.setLineMapper(studentLineMapper);

        return csvFileReader;
    }

    private LineMapper<StudentDTO> createStudentLineMapper() {
        DefaultLineMapper<StudentDTO> studentLineMapper = new DefaultLineMapper<>();

        LineTokenizer studentLineTokenizer = createStudentLineTokenizer();
        studentLineMapper.setLineTokenizer(studentLineTokenizer);

        FieldSetMapper<StudentDTO> studentInformationMapper = createStudentInformationMapper();
        studentLineMapper.setFieldSetMapper(studentInformationMapper);

        return studentLineMapper;
    }

    private LineTokenizer createStudentLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames(new String[]{"studentName", "emailAddress", "purchasedPackage"});
        return studentLineTokenizer;
    }

    private FieldSetMapper<StudentDTO> createStudentInformationMapper() {
        BeanWrapperFieldSetMapper<StudentDTO> studentInformationMapper = new BeanWrapperFieldSetMapper<>();
        studentInformationMapper.setTargetType(StudentDTO.class);
        return studentInformationMapper;
    }

    @Bean
    ItemProcessor<StudentDTO, CustomerDTO> csvFileItemProcessor() {
        return new StudentProcessor();
    }

    @Bean
    ItemWriter<CustomerDTO> csvFileDatabaseItemWriter() {
        JdbcBatchItemWriter<CustomerDTO> databaseItemWriter = new JdbcBatchItemWriter<>();
        DataSource dataSource = null;        
        try {
            dataSource = DerbyDbConfig.getBasicDS("APP", "APP");            
        } catch (SQLException ex) {
            Logger.getLogger(CsvFileToDatabaseJobConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        NamedParameterJdbcOperations jdbcOps = new NamedParameterJdbcTemplate(dataSource);
        databaseItemWriter.setDataSource(dataSource);
        databaseItemWriter.setJdbcTemplate(jdbcOps);

        databaseItemWriter.setSql(QUERY_INSERT_CUSTOMER);

        ItemSqlParameterSourceProvider<CustomerDTO> sqlParameterSourceProvider = customerSqlParameterSourceProvider();
        databaseItemWriter.setItemSqlParameterSourceProvider(sqlParameterSourceProvider);

        return databaseItemWriter;
    }

    private ItemSqlParameterSourceProvider<CustomerDTO> customerSqlParameterSourceProvider() {
        return new BeanPropertyItemSqlParameterSourceProvider<>();
    }

    @Bean
    Step csvFileToDatabaseStep(ItemReader<StudentDTO> csvFileItemReader,
                               ItemProcessor<StudentDTO, CustomerDTO> csvFileItemProcessor,
                               ItemWriter<CustomerDTO> csvFileDatabaseItemWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<StudentDTO, CustomerDTO>chunk(1)
                .reader(csvFileItemReader)
                .processor(csvFileItemProcessor)
                .writer(csvFileDatabaseItemWriter)
                .build();
    }

    @Bean
    Job csvFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
                             @Qualifier("csvFileToDatabaseStep") Step csvStudentStep) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(csvStudentStep)
                .end()
                .build();
    }
}
