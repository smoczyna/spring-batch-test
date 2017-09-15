/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class SubledgerCsvFileWriter extends CsvFileGenericWriter<SummarySubLedgerDTO> {
    
    public SubledgerCsvFileWriter(Environment environment) {
        super(environment, "subledger_summary.csv", new String[] {"jemsApplId",
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
    }
}
