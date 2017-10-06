/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.utils.FixedLengthLineAggregator;
import java.util.Map;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
/**
 *
 * @author smorcja
 * @param <T>
 */
public class FixedLengthGenericFileWriter<T> extends FlatFileItemWriter<T> {
    private final Class<T> payloadClass;
    
    public FixedLengthGenericFileWriter(Class<T> payloadClass, String fileName, Map<String, Integer> columns) {
        this.payloadClass = payloadClass;
        super.setAppendAllowed(true);
        this.setResource(new FileSystemResource(fileName));
        this.setLineAggregator(new FixedLengthLineAggregator(this.payloadClass, columns));
    }
}
