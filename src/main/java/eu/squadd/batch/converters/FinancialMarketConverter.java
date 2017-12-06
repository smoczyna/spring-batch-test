/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.converters;

import eu.squadd.batch.domain.mongo.FinancialMarket;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author smoczyna
 */
public class FinancialMarketConverter implements Converter<FinancialMarket, Document> {

    @Override
    public Document convert(FinancialMarket finMarket) {
        Document doc = new Document();
        doc.put("financialmarketid", finMarket.getFinancialmarketid());
        doc.put("fcccgsamapenddate", finMarket.getFcccgsamapenddate());
        doc.put("financialmarketmapenddate", finMarket.getFinancialmarketmapenddate());
        doc.put("glmarketlegalentityenddate", finMarket.getGlmarketlegalentityenddate());
        doc.put("glmarketmaptype", finMarket.getGlmarketmaptype());
        doc.put("glmarketenddate", finMarket.getGlmarketenddate());
        doc.put("alternatebookingtype", finMarket.getAlternatebookingtype());
        doc.put("fcccgsamapeffectivedate", finMarket.getFcccgsamapeffectivedate());
        doc.put("fcccgsanumber", finMarket.getFcccgsanumber());
        doc.put("financialmarketdefaultgeocode", finMarket.getFinancialmarketdefaultgeocode());
        doc.put("financialmarketeffectivedate", finMarket.getFinancialmarketeffectivedate());
        doc.put("financialmarketenddate", finMarket.getFinancialmarketenddate());
        doc.put("financialmarketmapeffectivedate", finMarket.getFinancialmarketmapeffectivedate());
        doc.put("financialmarketname", finMarket.getFinancialmarketname());
        doc.put("financialmarkettypecode", finMarket.getFinancialmarkettypecode());
        doc.put("gllegalentityid", finMarket.getGllegalentityid());
        doc.put("glmarketdescription", finMarket.getGlmarketdescription());
        doc.put("glmarketeffectivedate", finMarket.getGlmarketeffectivedate());
        doc.put("glmarketid", finMarket.getGlmarketid());
        doc.put("glmarketlegalentityeffectivedate", finMarket.getGlmarketlegalentityeffectivedate());
        doc.put("multiplemarketindicator", finMarket.getMultiplemarketindicator());
        doc.put("sidbid", finMarket.getSidbid());
        doc.put("dummy", finMarket.getDummy());
        return doc;
    }
    
    public FinancialMarket convert(Document document) {
        FinancialMarket fm = new FinancialMarket();
        fm.setFinancialmarketid(document.getString("financialmarketid"));
        fm.setFcccgsamapenddate(document.getString("fcccgsamapenddate"));
        fm.setFinancialmarketmapenddate(document.getString("glmarketlegalentityenddate"));
        fm.setGlmarketmaptype(document.getString("glmarketmaptype"));
        fm.setGlmarketenddate(document.getString("glmarketenddate"));
        fm.setAlternatebookingtype(document.getString("alternatebookingtype"));
        fm.setFcccgsamapeffectivedate(document.getString("fcccgsamapeffectivedate"));
        fm.setFcccgsanumber(document.getString("fcccgsanumber"));
        fm.setFinancialmarketdefaultgeocode(document.getString("financialmarketdefaultgeocode"));
        fm.setFinancialmarketeffectivedate(document.getString("financialmarketeffectivedate"));
        fm.setFinancialmarketenddate(document.getString("financialmarketenddate"));
        fm.setFinancialmarketmapeffectivedate(document.getString("financialmarketmapeffectivedate"));
        fm.setFinancialmarketname(document.getString("financialmarketname"));
        fm.setFinancialmarkettypecode(document.getString("financialmarkettypecode"));
        fm.setGllegalentityid(document.getString("gllegalentityid"));
        fm.setGlmarketdescription(document.getString("glmarketdescription"));
        fm.setGlmarketeffectivedate(document.getString("glmarketeffectivedate"));
        fm.setGlmarketid(document.getString("glmarketid"));
        fm.setGlmarketlegalentityeffectivedate(document.getString("glmarketlegalentityeffectivedate"));
        fm.setMultiplemarketindicator(document.getString("multiplemarketindicator"));
        fm.setSidbid(document.getString("sidbid"));
        fm.setDummy(document.getString("dummy"));
        return fm;
    }
}
