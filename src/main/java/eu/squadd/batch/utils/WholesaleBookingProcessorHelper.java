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
import org.springframework.batch.item.ExecutionContext;
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
    private long recordCount;
    private long zeroChargesCounter;
    private long gapsCounter;
    private long dataErrorsCounter;
    private long bypassCounter;
    private long subledgerWriteCounter;
    private long wholesaleReportCounter;
    private long maxSkippedRecords;
    private long numberOfChunks;
    private ExecutionContext stepExecutionContext;

    public WholesaleBookingProcessorHelper() {
        this.financialEventOffset = new HashMap();
        this.recordCount = 0L;
        this.zeroChargesCounter = 0;
        this.gapsCounter = 0;
        this.dataErrorsCounter = 0;
        this.bypassCounter = 0;
        this.subledgerWriteCounter = 0;
        this.wholesaleReportCounter = 0;
        this.maxSkippedRecords = 0;
    }

    public ExecutionContext getStepExecutionContext() {
        return stepExecutionContext;
    }

    public void setStepExecutionContext(ExecutionContext stepExecutionContext) {
        this.stepExecutionContext = stepExecutionContext;
    }

    public BookDateCsvFileDTO getDates() {
        return this.dates;
    }

    public void setDates(BookDateCsvFileDTO dates) {
        this.dates = dates;
    }

    public long getMaxSkippedRecords() {
        return this.maxSkippedRecords==0 ? Constants.DEFAULT_MAX_SKIPPED_RECORDS : this.maxSkippedRecords;
    }

    public void setMaxSkippedRecords(long maxSkippedRecords) {
        this.maxSkippedRecords = maxSkippedRecords>0 ? maxSkippedRecords : Constants.DEFAULT_MAX_SKIPPED_RECORDS;
    }

    public long getNumberOfChunks() {
        return this.numberOfChunks==0 ? Constants.DEFAULT_NUMBER_OF_CHUNKS : this.numberOfChunks;
    }

    public void setNumberOfChunks(long numberOfChunks) {
        this.numberOfChunks = numberOfChunks>0 ? numberOfChunks : Constants.DEFAULT_NUMBER_OF_CHUNKS;
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
            case Constants.RECORD_COUNT:
                this.recordCount++;
                break;
            case Constants.ZERO_CHARGES:
                this.zeroChargesCounter++;
                break;
            case Constants.GAPS:
                this.gapsCounter++;
                break;
            case Constants.DATA_ERRORS:
                this.dataErrorsCounter++;
                break;
            case Constants.BYPASS:
                this.bypassCounter++;
                break;
            case Constants.SUBLEDGER:
                this.subledgerWriteCounter++;
                break;
            case Constants.WHOLESALES_REPORT:
                this.wholesaleReportCounter++;
                break;
            default:
                break;
        }
    }

    public long getCounter(String name) {
        switch (name) {
            case Constants.RECORD_COUNT:
                return this.recordCount;
            case Constants.ZERO_CHARGES:
                return this.zeroChargesCounter;
            case Constants.GAPS:
                return this.gapsCounter;
            case Constants.DATA_ERRORS:
                return this.dataErrorsCounter;
            case Constants.BYPASS:
                return this.bypassCounter;
            case Constants.SUBLEDGER:
                return this.subledgerWriteCounter;
            case Constants.WHOLESALES_REPORT:
                return this.wholesaleReportCounter;
            default:
                return -1;
        }
    }

    public void clearCounters() {
        this.recordCount = 0;
        this.zeroChargesCounter = 0;
        this.gapsCounter = 0;
        this.dataErrorsCounter = 0;
        this.bypassCounter = 0;
        this.subledgerWriteCounter = 0;
        this.wholesaleReportCounter = 0;
    }
}
