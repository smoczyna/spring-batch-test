/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.dump.domain;

import java.io.Serializable;

/**
 *
 * @author smorcja
 */
public class BatchJobSeq implements Serializable {

    private Long id;
    private String dummy;

    public BatchJobSeq() {
    }

    public BatchJobSeq(Long id) {
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