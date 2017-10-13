/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import eu.squadd.batch.domain.casandra.DataEvent;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;
import eu.squadd.batch.domain.casandra.FinancialMarket;
import eu.squadd.batch.domain.casandra.WholesalePrice;
import eu.squadd.batch.domain.exceptions.NoResultsReturnedException;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author torelfa
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {CassandraQueryManager.class})
public class CassandraQueryManagerTest {

    @Autowired
    protected CassandraQueryManager queryManager;

    private static final String CASSANDRA_KEYSPACE = "j6_dev";

    private Session getCassandraSession() {
        AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
        Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();
        Session session = cluster.connect(CASSANDRA_KEYSPACE);
        return session;
    }

    //@Test //(expected = NoResultsReturnedException.class)
    public void testGetFinancialMarketRecord() throws Throwable {
        String financialmarketid = "HUB";
        List<FinancialMarket> recordsActual = queryManager.getFinancialMarketRecord(financialmarketid);
        assertTrue(recordsActual.size()>0);
        //assertTrue(recordsActual.size()==1);
        //List<FinancialMarket> recordsExpected = getFinancialMarketList();
        //Assert.assertEquals(recordsExpected, recordsActual);
    }

//    @Test
//    public void testIsWholesaleProduct() throws Throwable {
//        String homesidbid = "00000";
////        char occurred = queryManager.isWholesaleProduct(TmpProdId);
////        char occurredExpected = 'N';
//        // Assert.assertEquals(occurredExpected, occurred);
//        String query = "SELECT * FROM WholesalePrice WHERE homesidbid=?  ALLOW FILTERING";
//        PreparedStatement wholesalePriceStatement = getCassandraSession().prepare(query);
//        wholesalePriceStatement.bind(homesidbid);
//        Result<WholesalePrice> result;        
//        try {
//            result = new WholesalePriceCassandraMapper().executeAndMapResults(getCassandraSession(), wholesalePriceStatement, new MappingManager(getCassandraSession()), false);
//        } catch (NoHostAvailableException | QueryExecutionException | QueryValidationException | UnsupportedFeatureException e) {        
//        } catch (NullPointerException | InterruptedException | ExecutionException ex) {
//        }
//
//        for (WholesalePrice record : result) {
//            System.out.println(record.toString());
//        }
//    }

    //@Test //(expected = NoResultsReturnedException.class)
    public void testGetFinancialEventCategoryRecord() throws Throwable {
        Integer TmpProdId = 18958;
        //String financialmarketid = "K34";
        Integer InterExchangeCarrierCode = 0;
        String homesidequalsservingsidindicator = "Y";
        String alternatebookingindicator = "N";
        String debitCreditIndicator = "DR";
        List<FinancialEventCategory> recordsActual = queryManager.getFinancialEventCategoryNoClusteringRecord(TmpProdId, 
                homesidequalsservingsidindicator, alternatebookingindicator, InterExchangeCarrierCode, debitCreditIndicator);
        
        //List<FinancialEventCategory> recordsExpected = getFinancialEventCategoryLists();
        //Assert.assertEquals(recordsExpected, recordsActual);
    }

    //@Test(expected = NoResultsReturnedException.class)
    public void testGetDataEventRecords() throws Throwable {
        Integer productid = 1;
        List<DataEvent> dbResult = queryManager.getDataEventRecords(productid);
        //DataEvent recordExpected = getDataEventRecord();
        // Assert.assertEquals(recordExpected, record);
    }

    //@Test //(expected = NoResultsReturnedException.class)
    public void testGetProductRecords() throws Throwable {
        Integer productid = 19182;
        String homesidbid = "30332";
        List<WholesalePrice> dbResult = queryManager.getWholesalePriceRecords( productid, homesidbid);
        //WholesalePrice recordExpected = getWholesalePriceRecord();
        // Assert.assertEquals(recordExpected, record);
    }
}
