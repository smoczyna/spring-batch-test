/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.env.Environment;

/**
 *
 * @author smorcja
 */
public class BookDateTxtFileReader extends CsvFileGenericReader<BookDateCsvFileDTO> {
    
    public BookDateTxtFileReader(Environment environment, String filename) {
        super(BookDateCsvFileDTO.class, environment, filename, new String[]{"rptPerStartDate", "rptPerEndDate", "transPerStartDate", "transPerEndDate", "monthEndCycle"}, null);        
    }
    
    @Override
    protected LineTokenizer createLineTokenizer(String[] fieldNames, String delimiter) {
        FixedLengthTokenizer bookDateTokenizer = new FixedLengthTokenizer();
        bookDateTokenizer.setColumns(new Range[] {new Range(1, 10), new Range(11, 20), new Range(21, 30), new Range(31, 40), new Range(41, 42)});
        bookDateTokenizer.setNames(fieldNames);
        return bookDateTokenizer;
    }

    // it is too late to get this param here as class is instancialted before the step !!!
    
//    @BeforeStep
//    public void retrieveJobParams(StepExecution stepExecution) {
//        this.filename =  stepExecution.getJobParameters().getString("bookdate_txt_file_name");
//    }
}
