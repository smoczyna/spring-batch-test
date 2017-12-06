/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import eu.squadd.batch.converters.DataEventConverter;
import eu.squadd.batch.converters.FinancialEventCategoryConverter;
import eu.squadd.batch.domain.mongo.FinancialMarket;
import eu.squadd.batch.converters.FinancialMarketConverter;
import eu.squadd.batch.converters.WholesalePriceConverter;
import eu.squadd.batch.domain.mongo.DataEvent;
import eu.squadd.batch.domain.mongo.FinancialEventCategory;
import eu.squadd.batch.domain.mongo.WholesalePrice;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author smoczyna
 */
@Component
public class MongoQueryManager {

    private final MongoClient mongoClient;
    private final MongoDatabase db;

    public MongoQueryManager() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.db = mongoClient.getDatabase("j6_booking");
    }

    public List<FinancialMarket> getFinancialMarketRecords(String financialmarketid) {
        FinancialMarketConverter converter = new FinancialMarketConverter();
        Document search = new Document();
        search.put("financialmarketid", financialmarketid);
        FindIterable<Document> dbResult = db.getCollection("financial_market").find(search);
        List<FinancialMarket> list = new ArrayList();
        for (Document fec : dbResult) {
            list.add(converter.convert(fec));
        }
        return list;
    }

    public List<DataEvent> getDataEventRecords(Integer productid) {
        DataEventConverter converter = new DataEventConverter();
        Document search = new Document();
        search.put("productid", productid);
        FindIterable<Document> dbResult = db.getCollection("data_event").find(search);
        List<DataEvent> list = new ArrayList();
        for (Document de : dbResult) {
            list.add(converter.convert(de));
        }
        return list;        
    }

    public List<FinancialEventCategory> getFinancialEventCategoryRecords(Integer productid,
            String homesidequalsservingsidindicator, String alternatebookingindicator,
            int interexchangecarriercode, String financialeventnormalsign) {

        FinancialEventCategoryConverter converter = new FinancialEventCategoryConverter();
        Document search = new Document();
        search.put("productid", productid);
        search.put("homesidequalsservingsidindicator", homesidequalsservingsidindicator);
        search.put("alternatebookingindicator", alternatebookingindicator);
        search.put("interexchangecarriercode", interexchangecarriercode);
        search.put("financialeventnormalsign", financialeventnormalsign);
        
        FindIterable<Document> dbResult = db.getCollection("financial_event_category").find(search);
        List<FinancialEventCategory> list = new ArrayList();
        for (Document fec : dbResult) {
            list.add(converter.convert(fec));
        }
        return list; 
    }

    public List<WholesalePrice> getWholesalePriceRecords(Integer productid, String homesidbid) {
        WholesalePriceConverter converter = new WholesalePriceConverter();
        Document search = new Document();
        search.put("productid", productid);
        search.put("homesidbid", homesidbid);
        FindIterable<Document> dbResult = db.getCollection("wholesale_price").find(search);        
        List<WholesalePrice> list = new ArrayList();
        for (Document wp : dbResult) {
            list.add(converter.convert(wp));
        }
        return list; 
    }
}
