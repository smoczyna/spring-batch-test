/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author smorcja
 */
public class SubledgerFixedLengthFileWriter extends FixedLengthGenericFileWriter<SummarySubLedgerDTO> {
    private static final Map<String, Integer> FIELDS ;
    static {
        FIELDS = new LinkedHashMap();
        FIELDS.put("jemsApplId", 2);
        FIELDS.put("reportStartDate", 10);
        FIELDS.put("jemsApplTransactioDate", 10);
        FIELDS.put("financialEventNumber", 10);
        FIELDS.put("financialCategory", 10);
        FIELDS.put("financialmarketId", 3);
        FIELDS.put("subledgerSequenceNumber", 10);
        FIELDS.put("subledgerTotalDebitAmount", 14);
        FIELDS.put("subledgerTotalCreditAmount", 14);
        FIELDS.put("jurnalEventNumber", 10);
        FIELDS.put("jurnalEventExceptionCode", 4);
        FIELDS.put("jurnalEventReadInd", 1);
        FIELDS.put("generalLedgerTransactionNumber", 10);
        FIELDS.put("billCycleNumber", 2);
        FIELDS.put("billTypeCode", 2);
        FIELDS.put("billCycleMonthYear", 6);
        FIELDS.put("billPhaseType", 2);
        FIELDS.put("billMonthInd ", 1);
        FIELDS.put("billAccrualIndicator", 1);
        FIELDS.put("paymentSourceCode", 5);
        FIELDS.put("discountOfferId", 10);
        FIELDS.put("updateUserId", 8);
        FIELDS.put("updateTimestamp", 26);
    }
    
    public SubledgerFixedLengthFileWriter(String filePath) {
        super(SummarySubLedgerDTO.class, filePath, FIELDS);
    }    
}

