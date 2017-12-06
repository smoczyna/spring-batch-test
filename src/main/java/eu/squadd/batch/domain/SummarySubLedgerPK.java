/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

import java.util.Objects;

/**
 * primary key of the output record. 
 * It is a unique key for all output records
 * @author smorcja
 */
public class SummarySubLedgerPK {
    private Integer financialEventNo;
    private Integer financialCategory;
    private String financialMarketId;
        
    public SummarySubLedgerPK(Integer financialEventNo, Integer financialCategory, String financialMarketId) {
        this.financialEventNo = financialEventNo;
        this.financialCategory = financialCategory;
        this.financialMarketId = financialMarketId;
    }
    
    public Integer getFinancialEventNo() {
        return financialEventNo;
    }

    public void setFinancialEventNo(Integer financialEventNo) {
        this.financialEventNo = financialEventNo;
    }

    public Integer getFinancialCategory() {
        return financialCategory;
    }

    public void setFinancialCategory(Integer financialCategory) {
        this.financialCategory = financialCategory;
    }

    public String getFinancialMarketId() {
        return financialMarketId;
    }

    public void setFinancialMarketId(String financialMarketId) {
        this.financialMarketId = financialMarketId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.financialEventNo);
        hash = 59 * hash + Objects.hashCode(this.financialCategory);
        hash = 59 * hash + Objects.hashCode(this.financialMarketId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SummarySubLedgerPK other = (SummarySubLedgerPK) obj;        
        if (!Objects.equals(this.financialEventNo, other.financialEventNo)) {
            return false;
        }
        if (!Objects.equals(this.financialCategory, other.financialCategory)) {
            return false;
        }
        return Objects.equals(this.financialMarketId, other.financialMarketId);
    }
}
