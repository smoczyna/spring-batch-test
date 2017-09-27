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
public class WholesalePrice {
    
    private String HomeSidBid;   
    private Integer ProductId; 
    private String ServeSidBid;
    private String RatePeriodClassificationId;
    private String EffectiveDate;
    private String EndDate;
    private Double ProductWholesalePrice;    
    private Double ProductDiscountPercent;

    public String getHomeSidBid() {
        return HomeSidBid;
    }

    public void setHomeSidBid(String HomeSidBid) {
        this.HomeSidBid = HomeSidBid;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer ProductId) {
        this.ProductId = ProductId;
    }

    public String getServeSidBid() {
        return ServeSidBid;
    }

    public void setServeSidBid(String ServeSidBid) {
        this.ServeSidBid = ServeSidBid;
    }

    public String getRatePeriodClassificationId() {
        return RatePeriodClassificationId;
    }

    public void setRatePeriodClassificationId(String RatePeriodClassificationId) {
        this.RatePeriodClassificationId = RatePeriodClassificationId;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String EffectiveDate) {
        this.EffectiveDate = EffectiveDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }

    public Double getProductWholesalePrice() {
        return ProductWholesalePrice;
    }

    public void setProductWholesalePrice(Double ProductWholesalePrice) {
        this.ProductWholesalePrice = ProductWholesalePrice;
    }

    public Double getProductDiscountPercent() {
        return ProductDiscountPercent;
    }

    public void setProductDiscountPercent(Double ProductDiscountPercent) {
        this.ProductDiscountPercent = ProductDiscountPercent;
    }

    
}
