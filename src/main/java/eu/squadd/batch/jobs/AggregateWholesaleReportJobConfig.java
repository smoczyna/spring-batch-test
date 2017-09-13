package eu.squadd.batch.jobs;

import org.springframework.context.annotation.Configuration;

/**
 * @author smorcja
 */
@Configuration
public class AggregateWholesaleReportJobConfig {

//    @Bean
//    ItemReader<CustomerDTO> databaseCsvItemReader(Environment environment) throws Exception {
//        return new CustomerDbReader(environment, DatabasesConfig.getSampleDerbyDS());
//    }
//
//    @Bean
//    ItemProcessor<CustomerDTO, CustomerDTO> databaseCsvItemProcessor() {
//        return new CustomerProcessor();
//    }
//
//    @Bean
//    ItemWriter<CustomerDTO> databaseCsvItemWriter(Environment environment) {
//        return new CustomerCsvFileWriter(environment);
//    }
//
//    @Bean
//    Step aggregateWholesaleReportStep(ItemReader<CustomerDTO> databaseCsvItemReader,
//                                      ItemProcessor<CustomerDTO, CustomerDTO> databaseCsvItemProcessor,
//                                      ItemWriter<CustomerDTO> databaseCsvItemWriter,
//                                      StepBuilderFactory stepBuilderFactory) {
//        
//        return stepBuilderFactory.get("aggregateWholesaleReportStep")
//                .<CustomerDTO, CustomerDTO>chunk(1)
//                .reader(databaseCsvItemReader)
//                .processor(databaseCsvItemProcessor)
//                .writer(databaseCsvItemWriter)                
//                .build();
//    }
//
//    @Bean
//    Job aggregateWholesaleReportJob(JobBuilderFactory jobBuilderFactory,
//                                    @Qualifier("aggregateWholesaleReportStep") Step aggregateWholesaleReportStep) {
//        return jobBuilderFactory.get("aggregateWholesaleReportJob")
//                .incrementer(new RunIdIncrementer())
//                .flow(aggregateWholesaleReportStep)
//                .end()
//                .build();
//    }
}
