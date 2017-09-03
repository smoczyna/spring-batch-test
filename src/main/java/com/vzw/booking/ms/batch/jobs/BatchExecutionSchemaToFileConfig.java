/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.jobs;

import com.vzw.booking.ms.batch.dump.domain.BatchJobExecution;
import com.vzw.booking.ms.batch.dump.domain.BatchJobExecutionContext;
import com.vzw.booking.ms.batch.dump.domain.BatchJobExecutionParams;
import com.vzw.booking.ms.batch.dump.domain.BatchJobInstance;
import com.vzw.booking.ms.batch.dump.domain.BatchStepExecution;
import com.vzw.booking.ms.batch.dump.domain.BatchStepExecutionContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 *
 * @author smorcja
 */
@Configuration
public class BatchExecutionSchemaToFileConfig {
    
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutionSchemaToFileConfig.class);
    
    @Autowired
    private DataSource metaDataSource;
    
    /**
     * database readers      
     */
    
    @Bean
    ItemReader<BatchJobInstance> batchJobInstanceReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchJobInstance> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchJobInstance.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_JOB_INSTANCE");
        return databaseReader;
    }
    
    @Bean
    ItemReader<BatchJobExecution> batchJobExecutionReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchJobExecution> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchJobExecution.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_JOB_EXECUTION");
        return databaseReader;
    }
    
    @Bean
    ItemReader<BatchJobExecutionParams> batchJobExecutionParamsReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchJobExecutionParams> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchJobExecutionParams.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_JOB_EXECUTION_PARAMS");
        return databaseReader;
    }
    
    @Bean
    ItemReader<BatchStepExecution> batchStepExecutionReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchStepExecution> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchStepExecution.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_STEP_EXECUTION");
        return databaseReader;
    }
    
    @Bean
    ItemReader<BatchStepExecutionContext> batchStepExecutionCotextReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchStepExecutionContext> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchStepExecutionContext.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_STEP_EXECUTION_CONTEXT");
        return databaseReader;
    }
    
    @Bean
    ItemReader<BatchJobExecutionContext> batchJobExecutionContectReader(Environment environment) throws Exception {
        JdbcCursorItemReader<BatchJobExecutionContext> databaseReader = new JdbcCursorItemReader();
        databaseReader.setDataSource(metaDataSource);
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(BatchJobExecutionContext.class));
        databaseReader.setSql("SELECT * FROM BATCH.BATCH_JOB_EXECUTION_CONTEXT");
        return databaseReader;
    }
    
    /**
     * file writers      
     */
    
    @Bean
    ItemWriter<BatchJobInstance> batchJobInstanceWriter(Environment environment) {
        FlatFileItemWriter<BatchJobInstance> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-job-instance.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchJobInstance> lineAggregator = createBatchJobInstanceLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<BatchJobInstance> createBatchJobInstanceLineAggregator() {
        DelimitedLineAggregator<BatchJobInstance> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchJobInstance> fieldExtractor = createBatchJobInstanceFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchJobInstance> createBatchJobInstanceFieldExtractor() {
        BeanWrapperFieldExtractor<BatchJobInstance> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"jobInstanceId", "version", "jobName", "jobKey"});
        return extractor;
    }

    @Bean
    ItemWriter<BatchJobExecution> batchJobExecutionWriter(Environment environment) {
        FlatFileItemWriter<BatchJobExecution> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-job-execution.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchJobExecution> lineAggregator = createBatchJobExecutionLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);
        return csvFileWriter;
    }

    private LineAggregator<BatchJobExecution> createBatchJobExecutionLineAggregator() {
        DelimitedLineAggregator<BatchJobExecution> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchJobExecution> fieldExtractor = createBatchJobExecutioFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchJobExecution> createBatchJobExecutioFieldExtractor() {
        BeanWrapperFieldExtractor<BatchJobExecution> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"jobExecutionId", "version", "jobInstanceId", "createTime", "startTime", "status", 
                                         "exitCode", "exitMessage", "lastUpdated", "jobConfigurationLocation"});
        return extractor;
    }
    
    @Bean
    ItemWriter<BatchJobExecutionParams> batchJobExecutionParamsWriter(Environment environment) {
        FlatFileItemWriter<BatchJobExecutionParams> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-job-execution-params.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchJobExecutionParams> lineAggregator = createBatchJobExecutionParamsLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);
        return csvFileWriter;
    }

    private LineAggregator<BatchJobExecutionParams> createBatchJobExecutionParamsLineAggregator() {
        DelimitedLineAggregator<BatchJobExecutionParams> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchJobExecutionParams> fieldExtractor = createBatchJobExecutionParamsFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchJobExecutionParams> createBatchJobExecutionParamsFieldExtractor() {
        BeanWrapperFieldExtractor<BatchJobExecutionParams> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"jobExecutionId", "typeCd", "stringVal", "dateVal", "longVal", "doubleVal", "identifying"});
        return extractor;
    }
    
    @Bean
    ItemWriter<BatchJobExecutionContext> batchJobExecutionContextWriter(Environment environment) {
        FlatFileItemWriter<BatchJobExecutionContext> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-job-execution-cotext.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchJobExecutionContext> lineAggregator = createBatchJobExecutionContextLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);
        return csvFileWriter;
    }
    
    private LineAggregator<BatchJobExecutionContext> createBatchJobExecutionContextLineAggregator() {
        DelimitedLineAggregator<BatchJobExecutionContext> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchJobExecutionContext> fieldExtractor = createBatchJobExecutionContextFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchJobExecutionContext> createBatchJobExecutionContextFieldExtractor() {
        BeanWrapperFieldExtractor<BatchJobExecutionContext> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"jobExecutionId", "shortContext", "serializedContext"});
        return extractor;
    }
    
    @Bean
    ItemWriter<BatchStepExecution> batchStepExecutionWriter(Environment environment) {
        FlatFileItemWriter<BatchStepExecution> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-step-execution.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchStepExecution> lineAggregator = createBatchStepExecutionLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);
        return csvFileWriter;
    }

    private LineAggregator<BatchStepExecution> createBatchStepExecutionLineAggregator() {
        DelimitedLineAggregator<BatchStepExecution> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchStepExecution> fieldExtractor = createBatchStepExecutioFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchStepExecution> createBatchStepExecutioFieldExtractor() {
        BeanWrapperFieldExtractor<BatchStepExecution> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"stepExecutionId", "version", "stepName", "jobExecutionId", "startTime", "endTime", "status", 
                                         "commitCount", "readCount", "filterCount", "writeCount", "readSkipCount", "writeSkipCount", 
                                         "processSkipCount", "rollbackCount", "exitCode", "exitMessage", "lastUpdated"});
        return extractor;
    }
    
    @Bean
    ItemWriter<BatchStepExecutionContext> batchStepExecutionContextWriter(Environment environment) {
        FlatFileItemWriter<BatchStepExecutionContext> csvFileWriter = new FlatFileItemWriter<>();
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("batch-step-execution-context.csv");
        csvFileWriter.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<BatchStepExecutionContext> lineAggregator = createBatchStepExecutionContextLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);
        return csvFileWriter;
    }

    private LineAggregator<BatchStepExecutionContext> createBatchStepExecutionContextLineAggregator() {
        DelimitedLineAggregator<BatchStepExecutionContext> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<BatchStepExecutionContext> fieldExtractor = createBatchStepExecutioContextFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<BatchStepExecutionContext> createBatchStepExecutioContextFieldExtractor() {
        BeanWrapperFieldExtractor<BatchStepExecutionContext> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"stepExecutionId", "shortContext", "serializedContext"});
        return extractor;
    }
    
    /**
     * job steps     
     */
    
    @Bean
    Step jobInstanceToCsvFileStep(ItemReader<BatchJobInstance> batchJobInstanceReader,
                                  //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                  ItemWriter<BatchJobInstance> batchJobInstanceWriter,
                                  StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("jobInstanceToCsvFileStep")
                .<BatchJobInstance, BatchJobInstance>chunk(1)
                .reader(batchJobInstanceReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchJobInstanceWriter)
                .build();
    }

    @Bean
    Step jobExecutionToCsvFileStep(ItemReader<BatchJobExecution> batchJobExecutionReader,
                                  //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                  ItemWriter<BatchJobExecution> batchJobExecutioWriter,
                                  StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("jobExecutionToCsvFileStep")
                .<BatchJobExecution, BatchJobExecution>chunk(1)
                .reader(batchJobExecutionReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchJobExecutioWriter)
                .build();
    }
    
    @Bean
    Step jobExecutionParamsToCsvFileStep(ItemReader<BatchJobExecutionParams> batchJobExecutionParamsReader,
                                         //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                         ItemWriter<BatchJobExecutionParams> batchJobExecutionParamsWriter,
                                         StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("jobExecutionParamsToCsvFileStep")
                .<BatchJobExecutionParams, BatchJobExecutionParams>chunk(1)
                .reader(batchJobExecutionParamsReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchJobExecutionParamsWriter)
                .build();
    }
    
    @Bean
    Step jobExecutionContextToCsvFileStep(ItemReader<BatchJobExecutionContext> batchJobExecutionContextReader,
                                          //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                          ItemWriter<BatchJobExecutionContext> batchJobExecutionContextWriter,
                                          StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("jobExecutionContextToCsvFileStep")
                .<BatchJobExecutionContext, BatchJobExecutionContext>chunk(1)
                .reader(batchJobExecutionContextReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchJobExecutionContextWriter)
                .build();
    }
        
    @Bean
    Step stepExecutionToCsvFileStep(ItemReader<BatchStepExecution> batchStepExecutionReader,
                                    //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                    ItemWriter<BatchStepExecution> batchStepExecutionWriter,
                                    StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("stepExecutionToCsvFileStep")
                .<BatchStepExecution, BatchStepExecution>chunk(1)
                .reader(batchStepExecutionReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchStepExecutionWriter)
                .build();
    }
    
    @Bean
    Step stepExecutionContextToCsvFileStep(ItemReader<BatchStepExecutionContext> batchStepExecutionContextReader,
                                           //ItemProcessor<BatchJobInstance, BatchJobInstance> databaseCsvItemProcessor,
                                           ItemWriter<BatchStepExecutionContext> batchStepExecutionContextWriter,
                                           StepBuilderFactory stepBuilderFactory) {
        
        return stepBuilderFactory.get("stepExecutionContextToCsvFileStep")
                .<BatchStepExecutionContext, BatchStepExecutionContext>chunk(1)
                .reader(batchStepExecutionContextReader)
                //.processor(databaseCsvItemProcessor)
                .writer(batchStepExecutionContextWriter)
                .build();
    }
    
    @Bean
    Job batchSchemaDumpToCsvFileJob(JobBuilderFactory jobBuilderFactory, 
                                    JobCompletionNotificationListener listener,
                                    @Qualifier("jobInstanceToCsvFileStep") Step jobInstanceToCsvFileStep,
                                    @Qualifier("jobExecutionToCsvFileStep") Step jobExecutionToCsvFileStep,                                    
                                    @Qualifier("jobExecutionParamsToCsvFileStep") Step jobExecutionParamsToCsvFileStep,
                                    @Qualifier("jobExecutionContextToCsvFileStep") Step jobExecutionContextToCsvFileStep,
                                    @Qualifier("stepExecutionToCsvFileStep") Step stepExecutionToCsvFileStep,
                                    @Qualifier("stepExecutionContextToCsvFileStep") Step stepExecutionContextToCsvFileStep) {
        
        return jobBuilderFactory.get("batchSchemaDumpToCsvFileJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(jobInstanceToCsvFileStep)
                .next(jobExecutionToCsvFileStep)
                .next(jobExecutionParamsToCsvFileStep)
                .next(jobExecutionContextToCsvFileStep)
                .next(stepExecutionToCsvFileStep)
                .next(stepExecutionContextToCsvFileStep)
                .end()
                .build();
    }
}
