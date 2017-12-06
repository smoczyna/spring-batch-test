/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain.dump;

import java.io.Serializable;

/**
 *
 * @author smorcja
 */
public class BatchJobExecutionSeq implements Serializable {

    private Long id;
    private String dummy;

    public BatchJobExecutionSeq() {
    }

    public BatchJobExecutionSeq(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    } 
}
