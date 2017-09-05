package com.vzw.booking.ms.batch.writers;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * This class demonstrates how we can write add a header to the first line of
 * the exported CSV file that contains the student list.
 *
 * @author smorcja
 */
class StringHeaderWriter implements FlatFileHeaderCallback {

    private final String header;

    StringHeaderWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}
