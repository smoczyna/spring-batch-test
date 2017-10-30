package eu.squadd.batch.domain.mongo;

import java.math.BigDecimal;

public class WholesalePrice {

    private Integer productid;
    private String homesidbid;
    private String servesidbid;
    private String rateperiodclassificationid;
    private String effectivedate;
    private String enddate;
    private BigDecimal productwholesaleprice;
    private BigDecimal productdiscountpercent;
    private String dummy;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getHomesidbid() {
        return homesidbid;
    }

    public void setHomesidbid(String homesidbid) {
        this.homesidbid = homesidbid;
    }

    public String getServesidbid() {
        return servesidbid;
    }

    public void setServesidbid(String servesidbid) {
        this.servesidbid = servesidbid;
    }

    public String getRateperiodclassificationid() {
        return rateperiodclassificationid;
    }

    public void setRateperiodclassificationid(String rateperiodclassificationid) {
        this.rateperiodclassificationid = rateperiodclassificationid;
    }

    public String getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(String effectivedate) {
        this.effectivedate = effectivedate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public BigDecimal getProductwholesaleprice() {
        return productwholesaleprice;
    }

    public void setProductwholesaleprice(BigDecimal productwholesaleprice) {
        this.productwholesaleprice = productwholesaleprice;
    }

    public BigDecimal getProductdiscountpercent() {
        return productdiscountpercent;
    }

    public void setProductdiscountpercent(BigDecimal productdiscountpercent) {
        this.productdiscountpercent = productdiscountpercent;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    @Override
    public String toString() {
        return "WholesalePrice{" + "productid=" + productid + ", homesidbid=" + homesidbid + ", servesidbid=" + servesidbid + ", rateperiodclassificationid=" + rateperiodclassificationid + ", effectivedate=" + effectivedate + ", enddate=" + enddate + ", productwholesaleprice=" + productwholesaleprice + ", productdiscountpercent=" + productdiscountpercent + ", dummy=" + dummy + '}';
    }
}
