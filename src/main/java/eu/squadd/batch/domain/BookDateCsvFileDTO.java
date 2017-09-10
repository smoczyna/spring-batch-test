/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain;

/**
 *
 * @author smorcja
 */
public class BookDateCsvFileDTO {
    private String RPT_PER_START_DT;
    private String RPT_PER_END_DT;
    private String TRANS_PER_START_DT;
    private String TRANS_PER_END_DT;
    private String MONTH_END_CYCLE;

    public BookDateCsvFileDTO() {        
    }
    
    public BookDateCsvFileDTO(String RPT_PER_END_DT, String TRANS_PER_END_DT) {
        this.RPT_PER_END_DT = RPT_PER_END_DT;
        this.TRANS_PER_END_DT = TRANS_PER_END_DT;
    }
     
    public String getRPT_PER_START_DT() {
        return RPT_PER_START_DT;
    }

    public void setRPT_PER_START_DT(String RPT_PER_START_DT) {
        this.RPT_PER_START_DT = RPT_PER_START_DT;
    }

    public String getRPT_PER_END_DT() {
        return RPT_PER_END_DT;
    }

    public void setRPT_PER_END_DT(String RPT_PER_END_DT) {
        this.RPT_PER_END_DT = RPT_PER_END_DT;
    }

    public String getTRANS_PER_START_DT() {
        return TRANS_PER_START_DT;
    }

    public void setTRANS_PER_START_DT(String TRANS_PER_START_DT) {
        this.TRANS_PER_START_DT = TRANS_PER_START_DT;
    }

    public String getTRANS_PER_END_DT() {
        return TRANS_PER_END_DT;
    }

    public void setTRANS_PER_END_DT(String TRANS_PER_END_DT) {
        this.TRANS_PER_END_DT = TRANS_PER_END_DT;
    }

    public String getMONTH_END_CYCLE() {
        return MONTH_END_CYCLE;
    }

    public void setMONTH_END_CYCLE(String MONTH_END_CYCLE) {
        this.MONTH_END_CYCLE = MONTH_END_CYCLE;
    }
}
