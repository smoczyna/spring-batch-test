package com.vzw.booking.ms.batch.jobs;

import com.vzw.booking.ms.batch.config.DatabasesConfig;
import com.vzw.booking.ms.batch.processors.CustomerProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.vzw.booking.ms.batch.domain.CustomerDTO;
import com.vzw.booking.ms.batch.readers.CustomerDbReader;
import com.vzw.booking.ms.batch.writers.CustomerCsvFileWriter;
import org.springframework.core.env.Environment;

/**
 * @author Petri Kainulainen
 */
@Configuration
public class DatabaseToCsvFileJobConfig {

    /**
     * this bean is fixed now, it reads all records in the table 
     *
     * @return
     */
    @Bean
    ItemReader<CustomerDTO> databaseCsvItemReader(Environment environment) throws Exception {
        return new CustomerDbReader(environment, DatabasesConfig.getSampleDerbyDS());
    }

    @Bean
    ItemProcessor<CustomerDTO, CustomerDTO> databaseCsvItemProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    ItemWriter<CustomerDTO> databaseCsvItemWriter(Environment environment) {
        return new CustomerCsvFileWriter(environment);
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
