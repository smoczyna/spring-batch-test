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
import com.vzw.booking.ms.batch.domain.SummarySubLedgerDTO;
import com.vzw.booking.ms.batch.readers.CustomerDbReader;
import com.vzw.booking.ms.batch.writers.SubledgerCsvFileWriter;
import org.springframework.core.env.Environment;

/**
 * @author smorcja
 */
@Configuration
public class DatabaseToCsvFileJobConfig {

    @Bean
    ItemReader<CustomerDTO> customerDbItemReader(Environment environment) throws Exception {
        return new CustomerDbReader(environment, DatabasesConfig.getSampleDerbyDS());
    }

    @Bean
    ItemProcessor<CustomerDTO, SummarySubLedgerDTO> customerItemProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    ItemWriter<SummarySubLedgerDTO> subledgerItemWriter(Environment environment) {
        return new SubledgerCsvFileWriter(environment);
    }

    @Bean
    Step customerToSubledgerStep(ItemReader<CustomerDTO> customerDbItemReader,
                                 ItemProcessor<CustomerDTO, SummarySubLedgerDTO> customerItemProcessor,
                                 ItemWriter<SummarySubLedgerDTO> subledgerItemWriter,
                                 StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("databaseToCsvFileStep")
                .<CustomerDTO, SummarySubLedgerDTO>chunk(1)
                .reader(customerDbItemReader)
                .processor(customerItemProcessor)
                .writer(subledgerItemWriter)                
                .build();
    }

    @Bean
    Job databaseToCsvFileJob(JobBuilderFactory jobBuilderFactory,
                             @Qualifier("customerToSubledgerStep") Step customerToSubledgerStep) {
        return jobBuilderFactory.get("databaseToCsvFileJob")
                .incrementer(new RunIdIncrementer())
                .flow(customerToSubledgerStep)
                .end()
                .build();
    }
}
