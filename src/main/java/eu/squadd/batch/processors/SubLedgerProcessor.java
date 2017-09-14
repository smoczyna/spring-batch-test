/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.processors;

import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * this class does input record classification and consolidation
 * it retrieve or creates output record and populates it with all static data which are available at the moment
 * @author smorcja
 */
@Component
public class SubLedgerProcessor {

    private Set<SummarySubLedgerDTO> aggregatedOutput;
    private BookDateCsvFileDTO dates;

    public SubLedgerProcessor() {
        this.aggregatedOutput = new HashSet();
    }

    public Set< SummarySubLedgerDTO> getAggregatedOutput() {
        return aggregatedOutput;
    }

    public void setAggregatedOutput(Set<SummarySubLedgerDTO> aggregatedOutput) {
        this.aggregatedOutput = aggregatedOutput;
    }

    public SummarySubLedgerDTO add() {
        SummarySubLedgerDTO slRecord = new SummarySubLedgerDTO();
        if (this.dates != null) {
            slRecord.setReportStartDate(dates.getRptPerStartDate());
            slRecord.setJemsApplTransactioDate(dates.getTransPerEndDate());
        }        
        if (this.aggregatedOutput.add(slRecord))
            return slRecord;
        else
            return null;                    
    }

    public BookDateCsvFileDTO getDates() {
        return dates;
    }

    public void setDates(BookDateCsvFileDTO dates) {
        this.dates = dates;
    }
}
