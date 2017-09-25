/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.constants.Constants;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class SubledgerCsvFileWriter extends CsvFileGenericWriter<SummarySubLedgerDTO> {
    
    private static final String[] COLUMN_NAMES = new String[] {"jemsApplId",
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
                                                               "updateTimestamp"};
    @Autowired
    public SubledgerCsvFileWriter(Environment environment) {
        super(environment, Constants.SUBLEDGER_SUMMARY_FILENAME, COLUMN_NAMES);
    }
    
    public SubledgerCsvFileWriter(String filePath) {
        super(filePath, COLUMN_NAMES);
    }
}
