package eu.squadd.batch.jobs;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import eu.squadd.batch.domain.StudentDTO;
import eu.squadd.batch.domain.UserDTO;
import com.vzw.booking.ms.batch.processors.StudentToUserProcessor;
import eu.squadd.batch.readers.CsvFileGenericReader;
import eu.squadd.batch.readers.CsvFileReaderListener;
import eu.squadd.batch.writers.CasandraDbWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;


/**
 * @author smorcja
 */
@Configuration
public class CsvFileToDatabaseJobConfig {
    
    @Bean
    ItemReader<StudentDTO> studentFileItemReader(Environment environment) {        
        String[] fieldNames = new String[]{"studentName", "emailAddress", "purchasedPackage"};
        return new CsvFileGenericReader(StudentDTO.class, environment, "students.csv", fieldNames, ";", 1);
    }

    @Bean
    CsvFileReaderListener studentFileItemReaderListener() {
        return new CsvFileReaderListener();
    };
    
    @Bean
    ItemProcessor<StudentDTO, UserDTO> studentItemProcessor() {
        return new StudentToUserProcessor();
    }

//    @Bean
//    ItemWriter<CustomerDTO> csvFileDatabaseItemWriter() throws SQLException {
//        DataSource dataSource = DatabasesConfig.getSampleDerbyDS();
//        CustomerDbWriter databaseItemWriter = new CustomerDbWriter(dataSource);
//        return databaseItemWriter;
//    }

    @Bean
    ItemWriter<UserDTO> studentToUSerWriter() {
        CasandraDbWriter writer = new CasandraDbWriter();
        return writer;
    }
    
    @Bean
    Step csvFileToDatabaseStep(CsvFileReaderListener studentFileItemReaderListener,
                               ItemReader<StudentDTO> studentFileItemReader,
                               ItemProcessor<StudentDTO, UserDTO> studentItemProcessor,
                               ItemWriter<UserDTO> studentToUSerWriter,
                               StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<StudentDTO, UserDTO>chunk(1)
                .reader(studentFileItemReader)
                .processor(studentItemProcessor)
                .writer(studentToUSerWriter)
                .listener(studentFileItemReaderListener)
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
