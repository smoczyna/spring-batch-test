/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.WholesaleProcessingOutput;
import java.util.Collection;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class WholesaleOutputWriter implements ItemStreamWriter<WholesaleProcessingOutput> {

    @Autowired
    private WholesaleReportCsvWriter wholesaleReportWriter;
    
    @Autowired
    private SubledgerCsvFileWriter subledgerWriter;
    
    @Override
    public void open(ExecutionContext ec) throws ItemStreamException {
        this.wholesaleReportWriter.open(ec);
        this.subledgerWriter.open(ec);
    }

    @Override
    public void update(ExecutionContext ec) throws ItemStreamException {
        this.wholesaleReportWriter.update(ec);
        this.subledgerWriter.update(ec);
    }

    @Override
    public void close() throws ItemStreamException {
        this.wholesaleReportWriter.close();
        this.subledgerWriter.close();
    }

    @Override
    public void write(List<? extends WholesaleProcessingOutput> list) throws Exception {
        for (WholesaleProcessingOutput item : list) {
            this.wholesaleReportWriter.write(item.getWholesaleReportRecords());
            this.subledgerWriter.write(item.getSubledgerRecords());
        }        
    }    
}