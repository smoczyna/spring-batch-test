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
import eu.squadd.batch.domain.FinancialEventOffset;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.UnbilledCsvFileDTO;
import eu.squadd.batch.listeners.BookingAggregateJobListener;
import eu.squadd.batch.listeners.GenericStepExecutionListener;
import eu.squadd.batch.processors.BookDateProcessor;
import eu.squadd.batch.processors.FinancialEventOffsetProcessor;
import eu.squadd.batch.processors.SubLedgerProcessor;
import eu.squadd.batch.processors.WholesaleReportProcessor;
import eu.squadd.batch.readers.AdminFeesBookingFileReader;
import eu.squadd.batch.readers.BilledBookingFileReader;
import eu.squadd.batch.readers.BookDateCsvFileReader;
import eu.squadd.batch.readers.FinancialEventOffsetRader;
import eu.squadd.batch.readers.UnbilledBookingFileReader;
import eu.squadd.batch.validations.CsvFileVerificationSkipper;
import eu.squadd.batch.writers.AggregatedSubLedgerWriter;
import eu.squadd.batch.writers.SubledgerCsvFileWriter;
import eu.squadd.batch.writers.WholesaleReportCsvWriter;
import java.util.Set;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
@Configuration
public class BookigFilesJobConfig {
    
    /* listeners and checkers */
    
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
    SubLedgerProcessor tempSubLedgerOuput() {
        return new SubLedgerProcessor();
    }

    
    /* readers */
    
    @Bean
    ItemReader<BookDateCsvFileDTO> bookDateItemReader(Environment environment) {
        return new BookDateCsvFileReader(environment, Constants.BOOK_DATE_FILENAME);
    }

    @Bean
    ItemReader<FinancialEventOffset> financialEventOffsetReader(Environment environment) {
        return new FinancialEventOffsetRader(environment, Constants.FINANCIAL_EVENT_OFFSET_FILENAME);
    }
    
    @Bean
    ItemReader<BilledCsvFileDTO> billedFileItemReader(Environment environment) {
        return new BilledBookingFileReader(environment, Constants.BILLED_BOOKING_FILENAME);
    }

    @Bean
    ItemReader<UnbilledCsvFileDTO> unbilledFileItemReader(Environment environment) {
        return new UnbilledBookingFileReader(environment, Constants.UNBILLED_BOOKING_FILENAME);
    }
    
    @Bean
    ItemReader<AdminFeeCsvFileDTO> adminFeesFileItemReader(Environment environment) {
        return new AdminFeesBookingFileReader(environment, Constants.ADMIN_FEES_FILENAME);
    }
    
    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new CsvFileVerificationSkipper();
    }

    
    /* processors */
    
    @Bean
    ItemProcessor<BookDateCsvFileDTO, Set<SummarySubLedgerDTO>> bookDateProcessor() {
        return new BookDateProcessor();
    }
    
    @Bean
    ItemProcessor<FinancialEventOffset, Boolean> financialEventOffsetProcessor() {
        return new FinancialEventOffsetProcessor();
    }

    @Bean
    ItemProcessor<BilledCsvFileDTO, AggregateWholesaleReportDTO> billedBookingProcessor() {
        return new WholesaleReportProcessor();
    }

    @Bean
    ItemProcessor<UnbilledCsvFileDTO, AggregateWholesaleReportDTO> unbilledBookingProcessor() {
        return new WholesaleReportProcessor();
    }
    
    @Bean
    ItemProcessor<AdminFeeCsvFileDTO, AggregateWholesaleReportDTO> adminFeesBookingProcessor() {
        return new WholesaleReportProcessor();
    }

    
    /* writers */
    
    @Bean
    ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter(Environment environment) {
        return new WholesaleReportCsvWriter(environment);
    }
    
    @Bean
    ItemWriter<SummarySubLedgerDTO> subledgerItemWriter(Environment environment) {
        return new SubledgerCsvFileWriter(environment);
    }

    @Bean
    Tasklet writeAggregatedSubLedger() {
        return new AggregatedSubLedgerWriter();
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
                                ItemProcessor<BookDateCsvFileDTO, Set<SummarySubLedgerDTO>> bookDateProcessor,
                                StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("updateBookingDatesStep")
                .<BookDateCsvFileDTO, Set<SummarySubLedgerDTO>>chunk(1)
                .reader(bookDateItemReader)
                .processor(bookDateProcessor)
                .build();
    }

    @Bean
    Step readOffsetDataStep(ItemReader<FinancialEventOffset> financialEventOffsetReader,
                            ItemProcessor<FinancialEventOffset, Boolean> financialEventOffsetProcessor,
                            StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("readOffsetDataStep")
                .<FinancialEventOffset, Boolean>chunk(1)
                .reader(financialEventOffsetReader)
                .processor(financialEventOffsetProcessor)
                .build();
    }
    
    @Bean
    Step billedBookingFileStep(StepExecutionListener billedFileStepListener,
                               ItemReader<BilledCsvFileDTO> billedFileItemReader,
                               SkipPolicy fileVerificationSkipper,
                               ItemProcessor<BilledCsvFileDTO, AggregateWholesaleReportDTO> billedBookingProcessor,
                               ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("billedBookingFileStep")
                .<BilledCsvFileDTO, AggregateWholesaleReportDTO>chunk(1)
                .reader(billedFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(billedBookingProcessor)
                .writer(wholesaleReportWriter)
                .listener(billedFileStepListener)
                .build();
    }

    @Bean
    Step unbilledBookingFileStep(StepExecutionListener unbilledFileStepListener,
                                 ItemReader<UnbilledCsvFileDTO> unbilledFileItemReader,
                                 SkipPolicy fileVerificationSkipper,
                                 ItemProcessor<UnbilledCsvFileDTO, AggregateWholesaleReportDTO> unbilledBookingProcessor,
                                 ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("unbilledBookingFileStep")
                .<UnbilledCsvFileDTO, AggregateWholesaleReportDTO>chunk(1)
                .reader(unbilledFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(unbilledBookingProcessor)
                .writer(wholesaleReportWriter)
                .listener(unbilledFileStepListener)
                .build();
    }
    
    @Bean
    Step adminFeesBookingFileStep(StepExecutionListener adminFeesFileStepListener,
                                  ItemReader<AdminFeeCsvFileDTO> adminFeesFileItemReader,
                                  SkipPolicy fileVerificationSkipper,
                                  ItemProcessor<AdminFeeCsvFileDTO, AggregateWholesaleReportDTO> adminFeesBookingProcessor,
                                  ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter,
                                  StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("adminFeesBookingFileStep")
                .<AdminFeeCsvFileDTO, AggregateWholesaleReportDTO>chunk(1)
                .reader(adminFeesFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(adminFeesBookingProcessor)
                .writer(wholesaleReportWriter)
                .listener(adminFeesFileStepListener)
                .build();
    }
    
    @Bean
    Step saveSubLedgerToFile(Tasklet writeAggregatedSubLedger,
                             StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("saveSubLedgerToFile")
                .tasklet(writeAggregatedSubLedger)
                .build();
    }

    /* the job */
    
    @Bean
    Job bookingAggregateJob(JobExecutionListener bookingFileJobListener,
                            JobBuilderFactory jobBuilderFactory,
                            @Qualifier("checkIfSourceFilesExist") Step checkIfSourceFilesExist,
                            @Qualifier("updateBookingDatesStep") Step updateBookingDatesStep,
                            @Qualifier("readOffsetDataStep") Step readOffsetDataStep,
                            @Qualifier("billedBookingFileStep") Step billedBookingFileStep,
                            @Qualifier("unbilledBookingFileStep") Step unbilledBookingFileStep,
                            @Qualifier("adminFeesBookingFileStep") Step adminFeesBookingFileStep,
                            @Qualifier("saveSubLedgerToFile") Step saveSubLedgerToFile) {
        return jobBuilderFactory.get("bookingAggregateJob")
                .incrementer(new RunIdIncrementer())
                .listener(bookingFileJobListener)
                .start(checkIfSourceFilesExist)
                .on("COMPLETED").to(updateBookingDatesStep)
                .on("COMPLETED").to(readOffsetDataStep)
                .on("COMPLETED").to(billedBookingFileStep)
                .on("COMPLETED").to(unbilledBookingFileStep)
                .on("COMPLETED").to(adminFeesBookingFileStep)
                .on("COMPLETED").to(saveSubLedgerToFile)
                .end()
                .build();
    }
}
