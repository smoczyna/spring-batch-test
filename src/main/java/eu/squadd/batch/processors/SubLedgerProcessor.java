/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.domain.SummarySubLedgerPK;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * this class does input record classification and consolidation
 * it retrieve or creates output record and populates it with all static data which are available at the moment
 * @author smorcja
 */
@Component
public class SubLedgerProcessor {

    private Map<SummarySubLedgerPK, SummarySubLedgerDTO> aggregatedOutput;

    public SubLedgerProcessor() {
        this.aggregatedOutput = new ConcurrentHashMap();
    }

    public Map<SummarySubLedgerPK, SummarySubLedgerDTO> getAggregatedOutput() {
        return aggregatedOutput;
    }

    public void setAggregatedOutput(Map<SummarySubLedgerPK, SummarySubLedgerDTO> aggregatedOutput) {
        this.aggregatedOutput = aggregatedOutput;
    }

    public SummarySubLedgerDTO add(BilledCsvFileDTO fileRecord) {
        // caclulate PK
        // this is unknown yet so it is a fake here to prove whole stuff is working

        SummarySubLedgerPK pk = new SummarySubLedgerPK(1, 1, fileRecord.getFinancialMarket());
        SummarySubLedgerDTO value;
        if (this.aggregatedOutput.containsKey(pk)) {
            value = this.aggregatedOutput.get(pk);
            // update fields accordingly
        } else {
            value = new SummarySubLedgerDTO(pk);
            // assign the rest of values 
            this.aggregatedOutput.put(pk, value);
        }
        return value;
    }

    public void updateBookingDates(BookDateCsvFileDTO dates) {
        for (SummarySubLedgerDTO item : this.aggregatedOutput.values()) {
            item.setReportStartDate(dates.getRptPerStartDate());
            item.setJemsApplTransactioDate(dates.getTransPerEndDate());
        } 
    }
}
