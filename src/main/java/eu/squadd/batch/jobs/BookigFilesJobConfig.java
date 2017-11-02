/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import eu.squadd.batch.constants.Constants;
import eu.squadd.batch.domain.AdminFeeCsvFileDTO;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.FinancialEventOffsetDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import eu.squadd.batch.domain.WholesaleProcessingOutput;
import eu.squadd.batch.listeners.BookingAggregateJobListener;
import eu.squadd.batch.listeners.GenericStepExecutionListener;
import eu.squadd.batch.listeners.WholesaleProcessingListener;
import eu.squadd.batch.processors.BookDateProcessor;
import eu.squadd.batch.processors.FinancialEventOffsetProcessor;
import eu.squadd.batch.processors.WholesaleBookingProcessor;
import eu.squadd.batch.readers.AdminFeesBookingFileReader;
import eu.squadd.batch.readers.BilledBookingFileReader;
import eu.squadd.batch.readers.BookDateCsvFileReader;
import eu.squadd.batch.readers.FinancialEventOffsetReader;
import eu.squadd.batch.readers.UnbilledBookingFileReader;
import eu.squadd.batch.utils.RangePartitioner;
import eu.squadd.batch.validations.CsvFileVerificationSkipper;
import eu.squadd.batch.writers.SubledgerCsvFileWriter;
import eu.squadd.batch.writers.WholesaleOutputWriter;
import eu.squadd.batch.writers.WholesaleReportCsvWriter;
import java.io.IOException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author smorcja
 */
@Configuration
public class BookigFilesJobConfig {
    
    /* listeners and helpers */
    
//    @Autowired
//    WholesaleBookingProcessorHelper processingHelper;
    
    @Bean
    JobExecutionListener bookingFileJobListener() {
        return new BookingAggregateJobListener();
    }

    @Bean
    Tasklet sourceFilesExistanceChecker() {
        return new SourceFilesExistanceChecker();
    }

    @Bean
    StepExecutionListener bookingFileStepListener() {
        return new GenericStepExecutionListener();
    }

    @Bean
    WholesaleProcessingListener processingListener() {
        return new WholesaleProcessingListener();
    }
    
    @Bean
    @StepScope
    RangePartitioner billedFilePartitioner(Environment environment) {           
        return new RangePartitioner(environment, "billed_split/");
    }
    
    @Bean
    @StepScope
    RangePartitioner unbilledFilePartitioner(Environment environment) throws IOException {
        return new RangePartitioner(environment, "unbilled_split/");
    }

    @Bean
    @StepScope
    RangePartitioner adminFeesFilePartitioner(Environment environment) throws IOException {
        return new RangePartitioner(environment, "adminfees_split/");
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(1000);
        taskExecutor.setCorePoolSize(1000);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
    
    /* readers */
    
    @Bean
    ItemReader<BookDateCsvFileDTO> bookDateItemReader(Environment environment) {
        return new BookDateCsvFileReader(environment, Constants.BOOK_DATE_FILENAME);
    }

    @Bean
    ItemReader<FinancialEventOffsetDTO> financialEventOffsetReader(Environment environment) {
        return new FinancialEventOffsetReader(environment, Constants.FINANCIAL_EVENT_OFFSET_FILENAME);
    }

    @Bean
    @StepScope
    ItemReader<BilledCsvFileDTO> billedFileItemReader(@Value("#{stepExecutionContext[fileName]}") String filename) {
        return new BilledBookingFileReader(filename);
    }
    
    @Bean
    @StepScope
    ItemReader<UnbilledCsvFileDTO> unbilledFileItemReader(@Value("#{stepExecutionContext[fileName]}") String filename) {
        return new UnbilledBookingFileReader(filename);
    }
    
    @Bean
    @StepScope
    ItemReader<AdminFeeCsvFileDTO> adminFeesFileItemReader(@Value("#{stepExecutionContext[fileName]}") String filename) {
        return new AdminFeesBookingFileReader(filename);
    }
    
    
//    @Bean
//    @StepScope
//    BilledBookingFileReader billedFileItemReader() {
//        ExecutionContext context = this.processingHelper.getStepExecutionContext();
//        BilledBookingFileReader reader = new BilledBookingFileReader(context.getString("fileName"));
//        reader.open(context);
//        return reader;
//    }
//    
//    @Bean
//    @StepScope
//    UnbilledBookingFileReader unbilledFileItemReader() {
//        ExecutionContext context = this.processingHelper.getStepExecutionContext();
//        UnbilledBookingFileReader reader = new UnbilledBookingFileReader(context.getString("fileName"));
//        reader.open(context);
//        return reader;
//    }
//    
//    @Bean
//    @StepScope
//    AdminFeesBookingFileReader adminFeesFileItemReader() {
//        ExecutionContext context = this.processingHelper.getStepExecutionContext();
//        AdminFeesBookingFileReader reader = new AdminFeesBookingFileReader(context.getString("fileName"));
//        reader.open(context);
//        return reader;
//    }
    
    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new CsvFileVerificationSkipper();
    }

    /* processors */
    
    @Bean
    ItemProcessor<BookDateCsvFileDTO, Boolean> bookDateProcessor() {
        return new BookDateProcessor();
    }

    @Bean
    ItemProcessor<FinancialEventOffsetDTO, Boolean> financialEventOffsetProcessor() {
        return new FinancialEventOffsetProcessor();
    }

    @Bean
    @StepScope
    ItemProcessor<BilledCsvFileDTO, WholesaleProcessingOutput> billedBookingProcessor() {
        return new WholesaleBookingProcessor();
    }

    @Bean
    @StepScope
    ItemProcessor<UnbilledCsvFileDTO, WholesaleProcessingOutput> unbilledBookingProcessor() {
        return new WholesaleBookingProcessor();
    }

    @Bean
    @StepScope
    ItemProcessor<AdminFeeCsvFileDTO, WholesaleProcessingOutput> adminFeesBookingProcessor() {
        return new WholesaleBookingProcessor();
    }

    /* writers */
    
    @Bean
    @StepScope
    ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter(Environment environment) {
        return new WholesaleReportCsvWriter(environment);
    }

    @Bean
    @StepScope
    ItemWriter<SummarySubLedgerDTO> subledgerItemWriter(Environment environment) {
        return new SubledgerCsvFileWriter(environment);
    }

    @Bean
    @StepScope
    ItemWriter<WholesaleProcessingOutput> wholesaleOutputWriter(Environment environment) {
        return new WholesaleOutputWriter();
    }

    /* job steps */
    
    @Bean
    Step checkIfSourceFilesExist(Tasklet sourceFilesExistanceChecker,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("checkIfSourceFilesExist")
                .tasklet(sourceFilesExistanceChecker)
                .build();
    }

    @Bean
    Step updateBookingDatesStep(ItemReader<BookDateCsvFileDTO> bookDateItemReader,
                                ItemProcessor<BookDateCsvFileDTO, Boolean> bookDateProcessor,
                                StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("updateBookingDatesStep")
                .<BookDateCsvFileDTO, Boolean>chunk(1)
                .reader(bookDateItemReader)
                .processor(bookDateProcessor)
                .build();
    }

    @Bean
    Step readOffsetDataStep(ItemReader<FinancialEventOffsetDTO> financialEventOffsetReader,
                            ItemProcessor<FinancialEventOffsetDTO, Boolean> financialEventOffsetProcessor,
                            StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readOffsetDataStep")
                .<FinancialEventOffsetDTO, Boolean>chunk(1)
                .reader(financialEventOffsetReader)
                .processor(financialEventOffsetProcessor)
                .build();
    }

    @Bean
    Step billedFilePartitionStep(StepExecutionListener billedFileStepListener,
                                 RangePartitioner billedFilePartitioner,
                                 Step billedBookingFileSlaveStep,
                                 TaskExecutor taskExecutor,
                                 StepBuilderFactory stepBuilderFactory) { // throws UnexpectedInputException, MalformedURLException, ParseException {
        return stepBuilderFactory.get("billedFilePartitionStep")
                .partitioner("billedSlaveStep", billedFilePartitioner)
                .step(billedBookingFileSlaveStep)
                .taskExecutor(taskExecutor)
                .listener(billedFileStepListener)
                .build();
    }
    
    @Bean
    Step billedBookingFileSlaveStep(WholesaleProcessingListener processingListener,
                                    ItemReader<BilledCsvFileDTO> billedFileItemReader,
                                    SkipPolicy fileVerificationSkipper,
                                    ItemProcessor<BilledCsvFileDTO, WholesaleProcessingOutput> billedBookingProcessor,
                                    ItemWriter<WholesaleProcessingOutput> wholesaleOutputWriter,
                                    StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("billedBookingFileSlaveStep")
                .<BilledCsvFileDTO, WholesaleProcessingOutput>chunk(1)
                .reader(billedFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(billedBookingProcessor)
                .writer(wholesaleOutputWriter)
                .listener(processingListener) 
                .build();
    }

    @Bean
    Step unbilledFilePartitionStep(StepExecutionListener unbilledFileStepListener,
                                   RangePartitioner unbilledFilePartitioner,
                                   Step unbilledBookingFileSlaveStep,
                                   TaskExecutor taskExecutor,
                                   StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("unbilledFilePartitionStep")
                .partitioner("unbilledSlaveStep", unbilledFilePartitioner)
                .step(unbilledBookingFileSlaveStep)
                .taskExecutor(taskExecutor)
                .listener(unbilledFileStepListener)
                .build();
    }
    
    @Bean
    Step unbilledBookingFileSlaveStep(WholesaleProcessingListener processingListener,
                                      ItemReader<UnbilledCsvFileDTO> unbilledFileItemReader,
                                      SkipPolicy fileVerificationSkipper,
                                      ItemProcessor<UnbilledCsvFileDTO, WholesaleProcessingOutput> unbilledBookingProcessor,
                                      ItemWriter<WholesaleProcessingOutput> wholesaleOutputWriter,
                                      StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("unbilledBookingFileSlaveStep")
                .<UnbilledCsvFileDTO, WholesaleProcessingOutput>chunk(1)
                .reader(unbilledFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(unbilledBookingProcessor)
                .writer(wholesaleOutputWriter)
                .listener(processingListener) 
                .build();
    }

    @Bean
    Step adminFeesFilePartitionStep(StepExecutionListener adminFeesFileStepListener,
                                    RangePartitioner adminFeesFilePartitioner,
                                    Step adminFeesBookingFileSlaveStep,
                                    TaskExecutor taskExecutor,
                                    StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("adminFeesFilePartitionStep")
                .partitioner("adminFeesSlaveStep", adminFeesFilePartitioner)
                .step(adminFeesBookingFileSlaveStep)
                .taskExecutor(taskExecutor)
                .listener(adminFeesFileStepListener)
                .build();
    }
    
    @Bean
    Step adminFeesBookingFileSlaveStep(WholesaleProcessingListener processingListener,
                                       ItemReader<AdminFeeCsvFileDTO> adminFeesFileItemReader,
                                       SkipPolicy fileVerificationSkipper,
                                       ItemProcessor<AdminFeeCsvFileDTO, WholesaleProcessingOutput> adminFeesBookingProcessor,
                                       ItemWriter<WholesaleProcessingOutput> wholesaleOutputWriter,
                                       StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("adminFeesBookingFileSlaveStep")
                .<AdminFeeCsvFileDTO, WholesaleProcessingOutput>chunk(1)
                .reader(adminFeesFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(adminFeesBookingProcessor)
                .writer(wholesaleOutputWriter)
                .listener(processingListener) 
                .build();
    }

    /* the job */
    
    @Bean
    Job bookingAggregateJob(JobExecutionListener bookingFileJobListener,
                            JobBuilderFactory jobBuilderFactory,
                            @Qualifier("checkIfSourceFilesExist") Step checkIfSourceFilesExist,
                            @Qualifier("updateBookingDatesStep") Step updateBookingDatesStep,
                            @Qualifier("readOffsetDataStep") Step readOffsetDataStep,
                            @Qualifier("billedFilePartitionStep") Step billedFilePartitionStep,
                            @Qualifier("unbilledFilePartitionStep") Step unbilledFilePartitionStep,
                            @Qualifier("adminFeesFilePartitionStep") Step adminFeesFilePartitionStep) {
        return jobBuilderFactory.get("bookingAggregateJob")
                .incrementer(new RunIdIncrementer())
                .listener(bookingFileJobListener)
                .start(checkIfSourceFilesExist)
                .on("COMPLETED").to(updateBookingDatesStep)
                .on("COMPLETED").to(readOffsetDataStep)
                .on("COMPLETED").to(billedFilePartitionStep)
                .on("COMPLETED").to(unbilledFilePartitionStep)
                .on("COMPLETED").to(adminFeesFilePartitionStep)
                .end()
                .build();
    }
}
