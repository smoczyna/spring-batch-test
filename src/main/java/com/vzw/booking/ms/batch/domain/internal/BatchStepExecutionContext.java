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
public class BatchStepExecutionContext implements Serializable {

    private Long stepExecutionId;
    private String shortContext;
    private String serializedContext;
    //private BatchStepExecution batchStepExecution;

    public BatchStepExecutionContext() {
    }

    public BatchStepExecutionContext(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public BatchStepExecutionContext(Long stepExecutionId, String shortContext) {
        this.stepExecutionId = stepExecutionId;
        this.shortContext = shortContext;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public String getShortContext() {
        return shortContext;
    }

    public void setShortContext(String shortContext) {
        this.shortContext = shortContext;
    }

    public String getSerializedContext() {
        return serializedContext;
    }

    public void setSerializedContext(String serializedContext) {
        this.serializedContext = serializedContext;
    }

//    public BatchStepExecution getBatchStepExecution() {
//        return batchStepExecution;
//    }
//
//    public void setBatchStepExecution(BatchStepExecution batchStepExecution) {
//        this.batchStepExecution = batchStepExecution;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (stepExecutionId != null ? stepExecutionId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof BatchStepExecutionContext)) {
//            return false;
//        }
//        BatchStepExecutionContext other = (BatchStepExecutionContext) object;
//        if ((this.stepExecutionId == null && other.stepExecutionId != null) || (this.stepExecutionId != null && !this.stepExecutionId.equals(other.stepExecutionId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.vzw.booking.ms.batch.domain.internal.BatchStepExecutionContext[ stepExecutionId=" + stepExecutionId + " ]";
//    }
//    
}
