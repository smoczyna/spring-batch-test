/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.domain.internal;

import java.io.Serializable;

/**
 *
 * @author smorcja
 */
public class BatchStepExecutionSeq implements Serializable {

    private Long id;
    private String dummy;

    public BatchStepExecutionSeq() {
    }

    public BatchStepExecutionSeq(Long id) {
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
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof BatchStepExecutionSeq)) {
//            return false;
//        }
//        BatchStepExecutionSeq other = (BatchStepExecutionSeq) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.vzw.booking.ms.batch.domain.internal.BatchStepExecutionSeq[ id=" + id + " ]";
//    }
//    
}
