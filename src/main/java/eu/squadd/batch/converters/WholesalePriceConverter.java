/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.converters;

import eu.squadd.batch.domain.mongo.WholesalePrice;
import java.math.BigDecimal;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author smoczyna
 */
public class WholesalePriceConverter implements Converter<WholesalePrice, Document> {

    @Override
    public Document convert(WholesalePrice wsp) {
        Document doc = new Document();
        doc.put("productid", wsp.getProductid());
        doc.put("homesidbid", wsp.getHomesidbid());
        doc.put("servesidbid", wsp.getServesidbid());
        doc.put("effectivedate", wsp.getEffectivedate());
        doc.put("enddate", wsp.getEnddate());
        doc.put("productdiscountpercent", wsp.getProductdiscountpercent());
        doc.put("productwholesaleprice", wsp.getProductwholesaleprice());
        doc.put("rateperiodclassificationid", wsp.getRateperiodclassificationid());
        doc.put("dummy", wsp.getDummy());
        return doc;
    }
    
    public WholesalePrice convert(Document document) {
        WholesalePrice wsp = new WholesalePrice();
        wsp.setProductid(document.getInteger("productid"));
        wsp.setHomesidbid(document.getString("homesidbid"));
        wsp.setServesidbid(document.getString("servesidbid"));
        wsp.setEffectivedate(document.getString("effectivedate"));
        wsp.setEnddate(document.getString("enddate"));
        wsp.setProductdiscountpercent(BigDecimal.valueOf(document.getDouble("productdiscountpercent")));
        wsp.setProductwholesaleprice(BigDecimal.valueOf(document.getDouble("productwholesaleprice")));
        wsp.setRateperiodclassificationid(document.getString("rateperiodclassificationid"));
        wsp.setDummy(document.getString("dummy"));
        return wsp;
    }
}
