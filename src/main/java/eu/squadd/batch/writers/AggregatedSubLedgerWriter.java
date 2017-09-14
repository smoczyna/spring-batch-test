/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import eu.squadd.batch.domain.SummarySubLedgerDTO;
import eu.squadd.batch.processors.SubLedgerProcessor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class AggregatedSubLedgerWriter implements Tasklet {

    @Autowired
    SubLedgerProcessor tempSubLedgerOuput;
    
    @Autowired
    SubledgerCsvFileWriter writer;
    
    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
       List<SummarySubLedgerDTO> values = new ArrayList();
       values.addAll(tempSubLedgerOuput.getAggregatedOutput());
       writer.open(cc.getStepContext().getStepExecution().getExecutionContext());
       writer.write(values);
       writer.close();
       return RepeatStatus.FINISHED;
    }
    
}
