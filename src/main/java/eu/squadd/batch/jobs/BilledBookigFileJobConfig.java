/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import eu.squadd.batch.listeners.BilledBookingFileJobListener;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.processors.BookDateProcessor;
import eu.squadd.batch.processors.SubLedgerProcessor;
import eu.squadd.batch.processors.WholesaleReportProcessor;
import eu.squadd.batch.readers.BookDateCsvFileReader;
import eu.squadd.batch.readers.CsvFileGenericReader;
import eu.squadd.batch.listeners.BilledBookingFileStepExecutionListener;
import eu.squadd.batch.validation.CsvFileVerificationSkipper;
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
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
@Configuration
public class BilledBookigFileJobConfig {

    @Bean
    JobExecutionListener billedFileJobListener() {
        return new BilledBookingFileJobListener();
    }

    @Bean
    Tasklet sourceFilesExistanceChecker() {
        return new SourceFilesExistanceChecker();
    }

    @Bean
    StepExecutionListener billedFileStepListener() {
        return new BilledBookingFileStepExecutionListener();
    }

    @Bean
    SubLedgerProcessor tempSubLedgerOuput() {
        return new SubLedgerProcessor();
    }

    @Bean
    ItemReader<BilledCsvFileDTO> billedFileItemReader(Environment environment) {
        String[] fieldNames = new String[]{
            "homeSbid",
            "servingSbid",
            "messageSource",
            "incompleteInd",
            "airProdId",
            "incompleteProdId",
            "incompleteCallSurcharge",
            "airSurchargeProductId",
            "airSurcharge",
            "interExchangeCarrierCode",
            "tollProductId",
            "tollCharge",
            "tollSurchargeProductId",
            "tollSurcharge",
            "tollStateTax",
            "tollLocalTax",
            "localAirTax",
            "stateAirTax",
            "wholesalePeakAirCharge",
            "wholesaleOffPeakAirCharge",
            "wholesaleTollChargeLDPeak",
            "wholesaleTollChargeLDOther",
            "space",
            "financialMarket",
            "deviceType",
            "airBillSeconds",
            "tollBillSeconds",
            "wholesaleUsageBytes"};
        return new CsvFileGenericReader(BilledCsvFileDTO.class, environment, "bmdunld.csv", fieldNames, ";");
    }

    @Bean
    public SkipPolicy fileVerificationSkipper() {
        return new CsvFileVerificationSkipper();
    }

    @Bean
    ItemReader<BookDateCsvFileDTO> bookDateItemReader(Environment environment) {
        String[] fieldNames = new String[]{"rptPerStartDate", "rptPerEndDate", "transPerStartDate", "transPerEndDate", "monthEndCycle"};
        return new BookDateCsvFileReader(environment, "bookdate.txt", fieldNames);
    }

    @Bean
    ItemProcessor<BilledCsvFileDTO, AggregateWholesaleReportDTO> wholesaleBookingProcessor() {
        return new WholesaleReportProcessor();
    }

    @Bean
    ItemProcessor<BookDateCsvFileDTO, Set<SummarySubLedgerDTO>> bookDateProcessor() {
        return new BookDateProcessor();
    }

    @Bean
    ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter(Environment environment) {
        return new WholesaleReportCsvWriter(environment);
    }
        
    @Bean
    ItemWriter<SummarySubLedgerDTO> subledgerItemWriter(Environment environment) {
        return new SubledgerCsvFileWriter(environment);
    }

    @Bean
    //@DependsOn("com.vzw.booking.ms.batch.writers.SubledgerCsvFileWriter")
    Tasklet writeAggregatedSubLedger() {
        return new AggregatedSubLedgerWriter();
    }

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
    Step billedBookingFileStep(StepExecutionListener billedFileStepListener,
                               ItemReader<BilledCsvFileDTO> billedFileItemReader,
                               SkipPolicy fileVerificationSkipper,
                               ItemProcessor<BilledCsvFileDTO, AggregateWholesaleReportDTO> wholesaleBookingProcessor,
                               ItemWriter<AggregateWholesaleReportDTO> wholesaleReportWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("billedBookingFileStep")
                .<BilledCsvFileDTO, AggregateWholesaleReportDTO>chunk(1)
                .reader(billedFileItemReader)
                .faultTolerant()
                .skipPolicy(fileVerificationSkipper)
                .processor(wholesaleBookingProcessor)
                .writer(wholesaleReportWriter)
                .listener(billedFileStepListener)
                .build();
    }

    // other files steps here
    
    @Bean
    Step saveSubLedgerToFile(Tasklet writeAggregatedSubLedger,
                             StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("saveSubLedgerToFile")
                .tasklet(writeAggregatedSubLedger)
                .build();
    }

    @Bean
    Job billedBookingAggregateJob(JobExecutionListener billedFileJobListener,
                                  JobBuilderFactory jobBuilderFactory,
                                  @Qualifier("checkIfSourceFilesExist") Step checkIfSourceFilesExist,
                                  @Qualifier("updateBookingDatesStep") Step updateBookingDatesStep,
                                  @Qualifier("billedBookingFileStep") Step billedBookingFileStep,
                                  @Qualifier("saveSubLedgerToFile") Step saveSubLedgerToFile) {
        return jobBuilderFactory.get("billedBookingAggregateJob")
                .incrementer(new RunIdIncrementer())
                .listener(billedFileJobListener)
                .start(checkIfSourceFilesExist)
                .on("COMPLETED").to(updateBookingDatesStep)
                .on("COMPLETED").to(billedBookingFileStep)
                //other files steps
                .on("COMPLETED").to(saveSubLedgerToFile)
                .end()
                .build();
    }
}
