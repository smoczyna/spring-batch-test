package com.vzw.booking.ms.batch.jobs;

import com.vzw.booking.ms.batch.config.DatabasesConfig;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import com.vzw.booking.ms.batch.processors.StudentProcessor;
import com.vzw.booking.ms.batch.domain.CustomerDTO;
import com.vzw.booking.ms.batch.domain.StudentDTO;
import com.vzw.booking.ms.batch.readers.StrudentCsvFileReader;
import com.vzw.booking.ms.batch.readers.CsvFileReaderListener;
import com.vzw.booking.ms.batch.writers.CustomerDbWriter;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


/**
 * @author Petri Kainulainen
 */
@Configuration
public class CsvFileToDatabaseJobConfig {
    
    @Bean
    ItemReader<StudentDTO> csvFileItemReader(Environment environment) {        
        return new StrudentCsvFileReader(environment);
    }

    @Bean
    ItemProcessor<StudentDTO, CustomerDTO> csvFileItemProcessor() {
        return new StudentProcessor();
    }

    @Bean
    ItemWriter<CustomerDTO> csvFileDatabaseItemWriter() throws SQLException {
        DataSource dataSource = DatabasesConfig.getSampleDerbyDS();
        CustomerDbWriter databaseItemWriter = new CustomerDbWriter(dataSource);
        return databaseItemWriter;
    }

    @Bean
    CsvFileReaderListener csvFileItemReaderListener() {
        return new CsvFileReaderListener();
    };
    
    @Bean
    Step csvFileToDatabaseStep(CsvFileReaderListener csvFileItemReaderListener,
                               ItemReader<StudentDTO> csvFileItemReader,
                               ItemProcessor<StudentDTO, CustomerDTO> csvFileItemProcessor,
                               ItemWriter<CustomerDTO> csvFileDatabaseItemWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<StudentDTO, CustomerDTO>chunk(1)                
                .reader(csvFileItemReader)                
                .processor(csvFileItemProcessor)
                .writer(csvFileDatabaseItemWriter)    
                .listener(csvFileItemReaderListener)
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
