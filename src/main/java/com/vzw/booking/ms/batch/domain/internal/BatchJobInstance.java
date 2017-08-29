/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.domain.internal;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;

/**
 *
 * @author smorcja
 */
public class BatchJobInstance implements Serializable {

    private Long jobInstanceId;
    private BigInteger version;
    private String jobName;
    private String jobKey;
    //private Collection<BatchJobExecution> batchJobExecutionCollection;

    public BatchJobInstance() {
    }

    public BatchJobInstance(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BatchJobInstance(Long jobInstanceId, String jobName, String jobKey) {
        this.jobInstanceId = jobInstanceId;
        this.jobName = jobName;
        this.jobKey = jobKey;
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

//    public Collection<BatchJobExecution> getBatchJobExecutionCollection() {
//        return batchJobExecutionCollection;
//    }
//
//    public void setBatchJobExecutionCollection(Collection<BatchJobExecution> batchJobExecutionCollection) {
//        this.batchJobExecutionCollection = batchJobExecutionCollection;
//    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (jobInstanceId != null ? jobInstanceId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof BatchJobInstance)) {
//            return false;
//        }
//        BatchJobInstance other = (BatchJobInstance) object;
//        if ((this.jobInstanceId == null && other.jobInstanceId != null) || (this.jobInstanceId != null && !this.jobInstanceId.equals(other.jobInstanceId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.vzw.booking.ms.batch.domain.internal.BatchJobInstance[ jobInstanceId=" + jobInstanceId + " ]";
//    }
//    
}
