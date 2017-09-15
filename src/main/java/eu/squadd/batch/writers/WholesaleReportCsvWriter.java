/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author smorcja
 */
public class WholesaleReportCsvWriter extends FlatFileItemWriter<AggregateWholesaleReportDTO> {
    
    private static final String PROPERTY_CSV_EXPORT_FILE_PATH = "database.to.csv.job.export.file.path";
    
    public WholesaleReportCsvWriter(Environment environment) {
        String exportFilePath = environment.getRequiredProperty(PROPERTY_CSV_EXPORT_FILE_PATH).concat("wholesale_report.csv");
        this.setResource(new FileSystemResource(exportFilePath));
        LineAggregator<AggregateWholesaleReportDTO> lineAggregator = createWholesaleReportLineAggregator();
        this.setLineAggregator(lineAggregator);
    }
    
    private LineAggregator<AggregateWholesaleReportDTO> createWholesaleReportLineAggregator() {
        DelimitedLineAggregator<AggregateWholesaleReportDTO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        FieldExtractor<AggregateWholesaleReportDTO> fieldExtractor = createWholesaleReportFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);
        return lineAggregator;
    }
    
    private FieldExtractor<AggregateWholesaleReportDTO> createWholesaleReportFieldExtractor() {
        BeanWrapperFieldExtractor<AggregateWholesaleReportDTO> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"cycleMonthYear",
                                         "startDate",
                                         "endDate",
                                         "homeLegalEntity",
                                         "servingLegalEntity",
                                         "homeFinancialMarketId",
                                         "servingFinancialMarketId",
                                         "productDiscountOfferId",
                                         "contractTermId",
                                         "peakDollarAmt",
                                         "offpeakDollarAmt",
                                         "voiceMinutes",
                                         "tollDollarsAmt",
                                         "tollMinutes",
                                         "dollarAmt3G",
                                         "usage3G",
                                         "dollarAmt4G",
                                         "usage4G",
                                         "dollarAmtOther",
                                         "dbCrInd",
                                         "billedInd"});
        return extractor;
    }
}
