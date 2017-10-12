/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.utils;

import eu.squadd.batch.constants.Constants;
import eu.squadd.batch.domain.AggregateWholesaleReportDTO;
import eu.squadd.batch.domain.BookDateCsvFileDTO;
import eu.squadd.batch.domain.FinancialEventOffsetDTO;
import eu.squadd.batch.domain.SummarySubLedgerDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * this class does input record classification and consolidation it retrieve or
 * creates output record and populates it with all static data which are
 * available at the moment
 *
 * @author smorcja
 */
@Component
public class WholesaleBookingProcessorHelper {

    private BookDateCsvFileDTO dates;
    private final Map<Integer, Integer> financialEventOffset;
    private long zeroChargesCounter;
    private long gapsCounter;
    private long dataErrorsCounter;
    private long bypassCounter;
    private long subledgerWriteCounter;
    private long wholesaleReportCounter;
    private long maxSkippedRecords;

    public WholesaleBookingProcessorHelper() {
        this.financialEventOffset = new HashMap();
        this.zeroChargesCounter = 0;
        this.gapsCounter = 0;
        this.dataErrorsCounter = 0;
        this.bypassCounter = 0;
        this.subledgerWriteCounter = 0;
        this.wholesaleReportCounter = 0;
        this.maxSkippedRecords = 0;
    }

    public BookDateCsvFileDTO getDates() {
        return this.dates;
    }

    public void setDates(BookDateCsvFileDTO dates) {
        this.dates = dates;
    }

    public long getMaxSkippedRecords() {
        return this.maxSkippedRecords==0 ? Constants.MAX_SKIPPED_RECORDS : this.maxSkippedRecords;
    }

    public void setMaxSkippedRecords(long maxSkippedRecords) {
        this.maxSkippedRecords = maxSkippedRecords>0 ? maxSkippedRecords : Constants.MAX_SKIPPED_RECORDS;
    }

    public boolean addOffset(FinancialEventOffsetDTO offset) {
        this.financialEventOffset.put(offset.getFinancialEvent(), offset.getOffsetFinancialCategory());
        return true;
    }

    public Integer findOffsetFinCat(Integer finCat) {
        return this.financialEventOffset.get(finCat);
    }

    public SummarySubLedgerDTO addSubledger() {
        SummarySubLedgerDTO slRecord = new SummarySubLedgerDTO();
        if (this.dates != null) {
            slRecord.setReportStartDate(dates.getRptPerStartDate());
            slRecord.setJemsApplTransactioDate(dates.getTransPerEndDate());
        }
        this.subledgerWriteCounter++;
        return slRecord;                
    }
    
    public AggregateWholesaleReportDTO addWholesaleReport() {
        AggregateWholesaleReportDTO report = new AggregateWholesaleReportDTO();
        this.wholesaleReportCounter++;
        return report;
    }
    
    public void incrementCounter(String name) {
        switch (name) {
            case "zero":
                this.zeroChargesCounter++;
                break;
            case "gap":
                this.gapsCounter++;
                break;
            case "error":
                this.dataErrorsCounter++;
                break;
            case "bypass":
                this.bypassCounter++;
                break;
            case "subledger":
                this.subledgerWriteCounter++;
                break;
            case "report":
                this.wholesaleReportCounter++;
                break;
            default:
                break;
        }
    }

    public long getCounter(String name) {
        switch (name) {
            case "zero":
                return this.zeroChargesCounter;
            case "gap":
                return this.gapsCounter;
            case "error":
                return this.dataErrorsCounter;
            case "bypass":
                return this.bypassCounter;
            case "subledger":
                return this.subledgerWriteCounter;
            case "report":
                return this.wholesaleReportCounter;
            default:
                return -1;
        }
    }

    public void clearCounters() {
        this.zeroChargesCounter = 0;
        this.gapsCounter = 0;
        this.dataErrorsCounter = 0;
        this.bypassCounter = 0;
        this.subledgerWriteCounter = 0;
        this.wholesaleReportCounter = 0;
    }
}
