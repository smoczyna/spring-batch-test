/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.writers;

import com.vzw.booking.ms.batch.domain.SummarySubLedgerDTO;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author smorcja
 */
public class SubledgerCsvFileWriter extends FlatFileItemWriter<SummarySubLedgerDTO> {
    
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    public SubledgerCsvFileWriter(Environment environment) {
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("subledger_summary.csv");
        this.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<SummarySubLedgerDTO> lineAggregator = createCustomerLineAggregator();
        this.setLineAggregator(lineAggregator);
    }
    
    private LineAggregator<SummarySubLedgerDTO> createCustomerLineAggregator() {
        DelimitedLineAggregator<SummarySubLedgerDTO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        return lineAggregator;
    }
    
}
