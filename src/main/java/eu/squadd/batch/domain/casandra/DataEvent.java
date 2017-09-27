/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.domain.casandra;

/**
 *
 * @author smorcja
 */
public class DataEvent {
    
    private String DataEventType;
    private String ContentReportType;
    private String DataEventSubType;
    private String ProductBillLevelCode;
    private String ProductBillFrequencyCode;
    private String ContentComponentType;
    private String ContentRevenueshareType;
    private Integer ProductId;
    private String BillSectionCode;

    public String getDataEventType() {
        return DataEventType;
    }

    public void setDataEventType(String DataEventType) {
        this.DataEventType = DataEventType;
    }

    public String getContentReportType() {
        return ContentReportType;
    }

    public void setContentReportType(String ContentReportType) {
        this.ContentReportType = ContentReportType;
    }

    public String getDataEventSubType() {
        return DataEventSubType;
    }

    public void setDataEventSubType(String DataEventSubType) {
        this.DataEventSubType = DataEventSubType;
    }

    public String getProductBillLevelCode() {
        return ProductBillLevelCode;
    }

    public void setProductBillLevelCode(String ProductBillLevelCode) {
        this.ProductBillLevelCode = ProductBillLevelCode;
    }

    public String getProductBillFrequencyCode() {
        return ProductBillFrequencyCode;
    }

    public void setProductBillFrequencyCode(String ProductBillFrequencyCode) {
        this.ProductBillFrequencyCode = ProductBillFrequencyCode;
    }

    public String getContentComponentType() {
        return ContentComponentType;
    }

    public void setContentComponentType(String ContentComponentType) {
        this.ContentComponentType = ContentComponentType;
    }

    public String getContentRevenueshareType() {
        return ContentRevenueshareType;
    }

    public void setContentRevenueshareType(String ContentRevenueshareType) {
        this.ContentRevenueshareType = ContentRevenueshareType;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer ProductId) {
        this.ProductId = ProductId;
    }

    public String getBillSectionCode() {
        return BillSectionCode;
    }

    public void setBillSectionCode(String BillSectionCode) {
        this.BillSectionCode = BillSectionCode;
    }

    
}
