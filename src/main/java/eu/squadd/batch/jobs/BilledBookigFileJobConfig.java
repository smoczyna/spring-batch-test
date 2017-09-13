/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.SummarySubLedgerPK;
import eu.squadd.batch.processors.BilledFileProcessor;
import eu.squadd.batch.processors.BookDateProcessor;
import eu.squadd.batch.processors.SubLedgerProcessor;
import eu.squadd.batch.readers.BookDateCsvFileReader;
import eu.squadd.batch.readers.CsvFileGenericReader;
import eu.squadd.batch.readers.CsvFileReaderListener;
import eu.squadd.batch.writers.AggregatedSubLedgerWriter;
import eu.squadd.batch.writers.SubledgerCsvFileWriter;
import java.util.Map;
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
    StepExecutionListener csvFileReaderListener() {
        return new CsvFileReaderListener();
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
    
//    @Value("#{jobParameters['bookdate_txt_file_name']}")
//    private String BOOK_DATES_SOURCE_FILE_NAME;

    @Bean
    ItemReader<BookDateCsvFileDTO> bookDateItemReader(Environment environment) {
        String[] fieldNames = new String[]{"rptPerStartDate", "rptPerEndDate", "transPerStartDate", "transPerEndDate", "monthEndCycle"};
        return new BookDateCsvFileReader(environment, "bookdate.txt", fieldNames);
    }
    
    @Bean
    ItemProcessor<BilledCsvFileDTO, SummarySubLedgerDTO> billedFileProcessor() {
        return new BilledFileProcessor(); 
    }
    
    @Bean
    ItemProcessor<BookDateCsvFileDTO, Map<SummarySubLedgerPK, SummarySubLedgerDTO>> bookDateProcessor() {
        return new BookDateProcessor();
    }
    
//    @Bean
//    CompositeItemProcessor wholesaleBookingProcessor() {
//        CompositeItemProcessor<BilledCsvFileDTO, Map<SummarySubLedgerPK, SummarySubLedgerDTO>> compositeProcessor = new CompositeItemProcessor();
//        List processors = new ArrayList();
//        processors.add(new BilledFileProcessor());
//        processors.add(new BookDateProcessor());
//        compositeProcessor.setDelegates(processors);
//        return compositeProcessor;
//    }
          
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
    Step billedBookingFileStep(StepExecutionListener csvFileReaderListener,
                               ItemReader<BilledCsvFileDTO> billedFileItemReader,
                               ItemProcessor<BilledCsvFileDTO, SummarySubLedgerDTO> billedFileProcessor,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("billedBookingFileStep")
                .<BilledCsvFileDTO, SummarySubLedgerDTO>chunk(1)                
                .reader(billedFileItemReader)
                .processor(billedFileProcessor)
                .listener(csvFileReaderListener)
                .build();
    }

    @Bean
    Step updateBookingDatesStep(ItemReader<BookDateCsvFileDTO> bookDateItemReader,
                                ItemProcessor<BookDateCsvFileDTO, Map<SummarySubLedgerPK, SummarySubLedgerDTO>> bookDateProcessor,
                                StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("updateBookingDatesStep")
                .<BookDateCsvFileDTO, Map<SummarySubLedgerPK, SummarySubLedgerDTO>>chunk(1)
                .reader(bookDateItemReader)
                .processor(bookDateProcessor)
                .build();
    }
    
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
                                  @Qualifier("billedBookingFileStep") Step billedBookingFileStep,
                                  @Qualifier("updateBookingDatesStep") Step updateBookingDatesStep,
                                  @Qualifier("saveSubLedgerToFile") Step saveSubLedgerToFile) {
        return jobBuilderFactory.get("billedBookingAggregateJob")
                .incrementer(new RunIdIncrementer()) 
                .listener(billedFileJobListener)
                .start(checkIfSourceFilesExist)
                .on("COMPLETED").to(billedBookingFileStep)
                .on("COMPLETED").to(updateBookingDatesStep)
                .on("COMPLETED").to(saveSubLedgerToFile)
                .end()
                .build();
    }
}
