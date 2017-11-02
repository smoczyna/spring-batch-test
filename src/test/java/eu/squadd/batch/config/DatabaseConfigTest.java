/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.squadd.batch.converters.FinancialMarketConverter;
import eu.squadd.batch.domain.mongo.FinancialMarket;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smoczyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseConfigTest {

    private static MongoClient mongoClient;
    private MongoDatabase db;
    
    @Before
    public void setUp() {
        mongoClient = new MongoClient("localhost", 27017);
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger mongoLogger = loggerContext.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.INFO);
        this.db = mongoClient.getDatabase("j6_booking");
        
        //DB db = mongoClient.getDB("j6_booking");
        //boolean auth = db.authenticate("username", "password".toCharArray());
    }

    //@Test
    public void testDatabaseConnectivity() {
        List<String> dbs = mongoClient.getDatabaseNames();
        dbs.forEach((db) -> {
            System.out.println(db);
        });
    }

    @Test
    public void testCollections() {
        System.out.println("*** Checking number of records in each of mongo j6_booking table ***");
                
        MongoCollection finEvents = db.getCollection("financial_event_category");
        System.out.println("Fin Event record count: " + finEvents.count());

        MongoCollection finMarkets = db.getCollection("financial_market");
        System.out.println("Fin Market record count: " + finMarkets.count());

        MongoCollection dataEvents = this.db.getCollection("data_event");
        System.out.println("Data Event record count: " + dataEvents.count());
        
        MongoCollection wholesalePrices = this.db.getCollection("wholesale_price");
        System.out.println("Wholesale Price record count: " + wholesalePrices.count());
        
    }
    
    @Test
    public void testFindFinEventRecordsByProductId() {
        System.out.println("*** finds all records by productid, financial_event_category table");
        
        List<Document> criteria = new ArrayList();
        Document doc = Document.parse(
            "{'$group' : { '_id': '$productid', 'count': { '$sum': 1 } } }," +
            "{'$match': {'_id' :{ '$ne' : null } , 'count' : {'$gt': 1} } }," +
            "{'$project': {'productid' : '$_id', '_id' : 0} }," +
            "{'$sort': {'count' : -1} }");
        criteria.add(doc);
        int dupCount=0;
        AggregateIterable<Document> dbResult = db.getCollection("financial_event_category").aggregate(criteria);
        System.out.println("*** Financial Event Category contents ***");
        for (Document item : dbResult) {
            System.out.println(item.toJson());
            Integer value = (Integer) item.get("_id");
            Document search = Document.parse(
                    String.format("{'productid' : %d}", value)
            );
            FindIterable<Document> fecDuplicates = db.getCollection("financial_event_category").find(search);
            int fecCount=0;
            System.out.println("*** FEC List ***");
            for (Document fec : fecDuplicates) {
                System.out.println(fec.toJson());
                fecCount++;
//                if (fecCount>1)
//                    db.getCollection("financial_event_category").deleteOne(fec);
            }
            System.out.println("FEC Records found: "+fecCount);
            dupCount++;
        }
        System.out.println("Duplicates found: "+dupCount);
        System.out.println("*** end of contents ***");         
    }
    
    //@Test
    public void testFinMarketCollection() {
        System.out.println("*** testing mongo j6_booking db, financial_market table ***");
        
        FinancialMarketConverter converter = new FinancialMarketConverter();
//        Document search = Document.parse(
//                String.format("{'financialmarketid' : %d}", financialmarketid)
//        );
        String financialmarketid = "492";
        Document search = new Document();
        search.put("financialmarketid", financialmarketid);
        FindIterable<Document> dbResult = db.getCollection("financial_market").find(search);
        
        List<FinancialMarket> list = new ArrayList();
        int i=0;
        for (Document fec : dbResult) {
            list.add(converter.convert(fec));
            i++;
        }
        System.out.println("records found: " + i);
        list.forEach((finMarket) -> {
            System.out.println(finMarket.toString());
        });
//            System.out.println("fec: "+fec.toJson());
//            BsonSerializer.Deserialize<FinancialMarket>(fec);
    }
    
    @Test
    public void testWholesaleProceCollection() {
        System.out.println("*** testing wholesale proce collection, finding all records with productid=3 ***");
        
//        MongoCollection wholesalePrices = this.db.getCollection("wholesale_price");
//        wholesalePrices.aggregate(list)
        
        Integer productid = 3;
        Document search = new Document();
        search.put("productid", productid);        
        FindIterable<Document> dbResult = this.db.getCollection("wholesale_price").find(search);
        System.out.println("*** WhoesalePrice contents ***");
        int count=0;
        for (Document doc : dbResult) {            
            //System.out.println(doc.toJson());
            count++;
        }
        System.out.println("Records found: "+count);
        System.out.println("*** end of contents ***");
        
        System.out.println("number of records for each productid");
        
        List<Document> criteria = new ArrayList();
        Document doc = Document.parse(
            "{'$group' : { '_id': '$productid', 'count': { '$sum': 1 } } }," +
            "{'$match': {'_id' :{ '$ne' : null } , 'count' : {'$gt': 1} } }," +
            "{'$project': {'productid' : '$_id', '_id' : 0} }," +
            "{'$sort': {'count' : -1} }");
        criteria.add(doc);
        AggregateIterable<Document> dbResult2 = db.getCollection("wholesale_price").aggregate(criteria);
         for (Document item : dbResult2)
            System.out.println(item.toJson());
        
    }
}
