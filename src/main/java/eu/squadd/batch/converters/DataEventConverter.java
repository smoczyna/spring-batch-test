/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.converters;

import eu.squadd.batch.domain.mongo.DataEvent;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author smoczyna
 */
public class DataEventConverter implements Converter<DataEvent, Document> {

    @Override
    public Document convert(DataEvent dev) {
        Document doc = new Document();
        doc.put("productid", dev.getProductid());
        doc.put("dataeventsubtype", dev.getDataeventsubtype());
        doc.put("billsectioncode", dev.getBillsectioncode());
        doc.put("contentcomponenttype", dev.getContentcomponenttype());
        doc.put("contentreporttype", dev.getContentreporttype());
        doc.put("contentrevenuesharetype", dev.getContentrevenuesharetype());
        doc.put("dataeventtype", dev.getDataeventtype());
        doc.put("productbillfrequencycode", dev.getProductbillfrequencycode());
        doc.put("productbilllevelcode", dev.getProductbilllevelcode());
        doc.put("dummy", dev.getDummy());
        return doc;
    }
    
    public DataEvent convert(Document document) {
        DataEvent de = new DataEvent();
        de.setProductid(document.getInteger("productid"));
        de.setDataeventsubtype(document.getString("dataeventsubtype"));
        de.setBillsectioncode(document.getString("billsectioncode"));
        de.setContentcomponenttype(document.getString("contentcomponenttype"));
        de.setContentreporttype(document.getString("contentreporttype"));
        de.setContentrevenuesharetype(document.getString("contentrevenuesharetype"));
        de.setDataeventtype(document.getString("dataeventtype"));
        de.setProductbillfrequencycode(document.getString("productbillfrequencycode"));
        de.setProductbilllevelcode(document.getString("productbilllevelcode"));
        de.setDummy(document.getString("dummy"));
        return de;
    }
}
