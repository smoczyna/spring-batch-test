package eu.squadd.batch.domain.mongo;

public class DataEvent {

    private Integer productid;
    private String dataeventtype;
    private String dataeventsubtype;
    private String contentreporttype;
    private String productbilllevelcode;
    private String productbillfrequencycode;
    private String contentcomponenttype;
    private String contentrevenuesharetype;
    private String billsectioncode;    
    private String dummy;

    public String getDataeventtype() {
        return dataeventtype;
    }

    public void setDataeventtype(String dataeventtype) {
        this.dataeventtype = dataeventtype;
    }

    public String getContentreporttype() {
        return contentreporttype;
    }

    public void setContentreporttype(String contentreporttype) {
        this.contentreporttype = contentreporttype;
    }

    public String getDataeventsubtype() {
        return dataeventsubtype;
    }

    public void setDataeventsubtype(String dataeventsubtype) {
        this.dataeventsubtype = dataeventsubtype;
    }

    public String getProductbilllevelcode() {
        return productbilllevelcode;
    }

    public void setProductbilllevelcode(String productbilllevelcode) {
        this.productbilllevelcode = productbilllevelcode;
    }

    public String getProductbillfrequencycode() {
        return productbillfrequencycode;
    }

    public void setProductbillfrequencycode(String productbillfrequencycode) {
        this.productbillfrequencycode = productbillfrequencycode;
    }

    public String getContentcomponenttype() {
        return contentcomponenttype;
    }

    public void setContentcomponenttype(String contentcomponenttype) {
        this.contentcomponenttype = contentcomponenttype;
    }

    public String getContentrevenuesharetype() {
        return contentrevenuesharetype;
    }

    public void setContentrevenuesharetype(String contentrevenuesharetype) {
        this.contentrevenuesharetype = contentrevenuesharetype;
    }

    public String getBillsectioncode() {
        return billsectioncode;
    }

    public void setBillsectioncode(String billsectioncode) {
        this.billsectioncode = billsectioncode;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    @Override
    public String toString() {
        return "DataEvent{" + "productid=" + productid + ", dataeventtype=" + dataeventtype + ", dataeventsubtype=" + dataeventsubtype + ", contentreporttype=" + contentreporttype + ", productbilllevelcode=" + productbilllevelcode + ", productbillfrequencycode=" + productbillfrequencycode + ", contentcomponenttype=" + contentcomponenttype + ", contentrevenuesharetype=" + contentrevenuesharetype + ", billsectioncode=" + billsectioncode + ", dummy=" + dummy + '}';
    }
}
