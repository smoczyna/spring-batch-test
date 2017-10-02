/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import java.util.ArrayList;
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
import eu.squadd.batch.domain.casandra.exceptions.NoResultsReturnedException;

/**
 *
 * @author torelfa
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CassandraQueryManager.class })
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

	@Test(expected = NoResultsReturnedException.class)
	public void testGetFinancialMarketRecord() throws Throwable {
		String file2financialmarketid = "1";
		List<FinancialMarket> recordsActual = queryManager.getFinancialMarketRecord(getCassandraSession(), file2financialmarketid);
		List<FinancialMarket> recordsExpected = getFinancialMarketList();
		// Assert.assertEquals(recordsExpected, recordsActual);
	}

	@Test
	public void testIsWholesaleProduct() throws Throwable {
		Integer TmpProdId = 1;
		char occurred = queryManager.isWholesaleProduct(getCassandraSession(), TmpProdId);
		char occurredExpected = 'N';
		// Assert.assertEquals(occurredExpected, occurred);
	}

	@Test(expected = NoResultsReturnedException.class)
	public void testGetFinancialEventCategoryRecord() throws Throwable {
		Integer TmpProdId = 1;
		String File2FinancialMarketId = "2";
		Integer InterExchangeCarrierCode = 3;
		String homesidequalsservingsidindicator = "4";
		String alternatebookingindicator = "N";
		List<FinancialEventCategory> recordsActual = queryManager.getFinancialEventCategoryRecord(getCassandraSession(),
				TmpProdId, File2FinancialMarketId, InterExchangeCarrierCode, homesidequalsservingsidindicator,
				alternatebookingindicator);
		List<FinancialEventCategory> recordsExpected = getFinancialEventCategoryLists();
		// Assert.assertEquals(recordsExpected, recordsActual);

	}

	@Test (expected = NoResultsReturnedException.class)
	public void testGetDataEventRecords() throws Throwable {
		Integer productid = 1;
		List<DataEvent> record = queryManager.getDataEventRecords(getCassandraSession(), productid);
		DataEvent recordExpected = getDataEventRecord();
		// Assert.assertEquals(recordExpected, record);
	}

	@Test(expected = NoResultsReturnedException.class)
	public void testgetProductRecords() throws Throwable {
		Integer productid = 1;
		String homesidbid = "a";
		List<WholesalePrice> record = queryManager.getWholesalePriceRecords(getCassandraSession(), productid, homesidbid);
		WholesalePrice recordExpected = getWholesalePriceRecord();
		// Assert.assertEquals(recordExpected, record);
	}

	private WholesalePrice getWholesalePriceRecord() {
		WholesalePrice wp = new WholesalePrice();
		wp.setProductwholesaleprice(1.1);
		wp.setProductdiscountpercent(2.1);
		return wp;
	}

	List<FinancialMarket> getFinancialMarketList() {
		List<FinancialMarket> listoffm = new ArrayList<>();
		FinancialMarket fm = new FinancialMarket();
		fm.setSidbid("a");
		fm.setAlternatebookingtype("a");
		fm.setGllegalentityid("a");
		fm.setGlmarketmaptype("a");
		return listoffm;
	}

	List<FinancialEventCategory> getFinancialEventCategoryLists() {
		List<FinancialEventCategory> listoffec = new ArrayList<>();
		FinancialEventCategory fec = new FinancialEventCategory();

		fec.setBamsaffiliateindicator("a");
		fec.setCompanycode("a");
		fec.setForeignservedindicator("a");
		fec.setHomesidequalsservingsidindicator("a");
		fec.setAlternatebookingindicator("a");
		listoffec.add(fec);
		return listoffec;
	}

	List<DataEvent> getDataEventList() {
		List<DataEvent> listofde = new ArrayList<>();
		DataEvent de = new DataEvent();
		de.setDataeventsubtype("a");
		listofde.add(de);
		return listofde;
	}

	DataEvent getDataEventRecord() {
		DataEvent de = new DataEvent();
		de.setDataeventsubtype("a");
		return de;
	}

}
