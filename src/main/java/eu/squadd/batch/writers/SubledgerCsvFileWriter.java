/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class SubledgerCsvFileWriter extends FlatFileItemWriter<SummarySubLedgerDTO> {
    
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    public SubledgerCsvFileWriter(Environment environment) {
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("subledger_summary.csv");
        this.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<SummarySubLedgerDTO> lineAggregator = createSubLedgerLineAggregator();
        this.setLineAggregator(lineAggregator);
    }
    
    private LineAggregator<SummarySubLedgerDTO> createSubLedgerLineAggregator() {
        DelimitedLineAggregator<SummarySubLedgerDTO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<SummarySubLedgerDTO> fieldExtractor = createSubledgerFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<SummarySubLedgerDTO> createSubledgerFieldExtractor() {
        BeanWrapperFieldExtractor<SummarySubLedgerDTO> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"jemsApplId",
                                         "reportStartDate",
                                         "jemsApplTransactioDate",
                                         "financialEventNumber",
                                         "financialCategory", 
                                         "financialmarketId",
                                         "subledgerSequenceNumber",
                                         "subledgerTotalDebitAmount",
                                         "subledgerTotalCreditAmount",
                                         "jurnalEventNumber",
                                         "jurnalEventExceptionCode",
                                         "jurnalEventReadInd",
                                         "generalLedgerTransactionNumber",
                                         "billCycleNumber",
                                         "billTypeCode",
                                         "billCycleMonthYear",
                                         "billPhaseType",
                                         "billMonthInd",
                                         "billAccrualIndicator",
                                         "paymentSourceCode",
                                         "discountOfferId",
                                         "updateUserId",
                                         "updateTimestamp"});
        return extractor;
    }

}
