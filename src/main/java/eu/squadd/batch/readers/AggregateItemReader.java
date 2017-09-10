/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import eu.squadd.batch.domain.AggregateItem;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

/**
 *
 * @author smorcja
 * @param <T>
 */
public class AggregateItemReader<T> implements ItemReader<List<T>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateItemReader.class);

    private ItemReader<AggregateItem<T>> itemReader;

    /**
     * Get the next list of records.
     * @return 
     * @throws java.lang.Exception 
     * @see org.springframework.batch.item.ItemReader#read()
     */
    @Override
    public List<T> read() throws Exception {
        ResultHolder holder = new ResultHolder();

        while (process(itemReader.read(), holder)) {
        }

        if (!holder.isExhausted()) {
            return holder.getRecords();
        } else {
            return null;
        }
    }

    private boolean process(AggregateItem<T> value, ResultHolder holder) {
        // finish processing if we hit the end of file
        if (value == null) {
            LOGGER.debug("Exhausted ItemReader");
            holder.setExhausted(true);
            return false;
        }

        // start a new collection
        if (value.isHeader()) {
            LOGGER.debug("Start of new record detected");
            return true;
        }

        // mark we are finished with current collection
        if (value.isFooter()) {
            LOGGER.debug("End of record detected");
            return false;
        }

        // add a simple record to the current collection
        LOGGER.debug("Mapping: " + value);
        holder.addRecord(value.getItem());
        return true;
    }

    public void setItemReader(ItemReader<AggregateItem<T>> itemReader) {
        this.itemReader = itemReader;
    }

    /**
     * Private class for temporary state management while item is being
     * collected.
     *
     * @author smorcja
     *
     */
    private class ResultHolder {

        private final List<T> records = new ArrayList();
        private boolean exhausted = false;

        public List<T> getRecords() {
            return records;
        }

        public boolean isExhausted() {
            return exhausted;
        }

        public void addRecord(T record) {
            records.add(record);
        }

        public void setExhausted(boolean exhausted) {
            this.exhausted = exhausted;
        }
    }
}
