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
public class BatchJobExecutionContext implements Serializable {

    private Long jobExecutionId;
    private String shortContext;
    private String serializedContext;
    //private BatchJobExecution batchJobExecution;

    public BatchJobExecutionContext() {
    }

    public BatchJobExecutionContext(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public BatchJobExecutionContext(Long jobExecutionId, String shortContext) {
        this.jobExecutionId = jobExecutionId;
        this.shortContext = shortContext;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
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

//    public BatchJobExecution getBatchJobExecution() {
//        return batchJobExecution;
//    }
//
//    public void setBatchJobExecution(BatchJobExecution batchJobExecution) {
//        this.batchJobExecution = batchJobExecution;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (jobExecutionId != null ? jobExecutionId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof BatchJobExecutionContext)) {
//            return false;
//        }
//        BatchJobExecutionContext other = (BatchJobExecutionContext) object;
//        if ((this.jobExecutionId == null && other.jobExecutionId != null) || (this.jobExecutionId != null && !this.jobExecutionId.equals(other.jobExecutionId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.vzw.booking.ms.batch.domain.internal.BatchJobExecutionContext[ jobExecutionId=" + jobExecutionId + " ]";
//    }
//    
}
