package eu.squadd.batch.config;

import com.datastax.driver.core.AuthProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.datastax.driver.core.exceptions.UnsupportedFeatureException;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import eu.squadd.batch.domain.casandra.DataEvent;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;
import eu.squadd.batch.domain.casandra.FinancialMarket;
import eu.squadd.batch.domain.casandra.Product;
import eu.squadd.batch.domain.casandra.WholesalePrice;
import eu.squadd.batch.domain.casandra.exceptions.CassandraQueryException;
import eu.squadd.batch.domain.casandra.exceptions.ErrorEnum;
import eu.squadd.batch.domain.casandra.exceptions.MultipleRowsReturnedException;
import eu.squadd.batch.domain.casandra.exceptions.NoResultsReturnedException;
import eu.squadd.batch.domain.casandra.mappers.*;

/**
 *
 * @author khanaas
 */
@Configuration
@PropertySource("classpath:cassandra.properties")
public class CassandraQueryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraQueryManager.class);
    private static Session session = null;

    @Value("${com.vzw.services.cassandra.FccCgsaMapEndDate}")
    private String fcccgsamapenddate;

    @Value("${com.vzw.services.cassandra.FinancialMarketMapEndDate}")
    private String financialmarketmapenddate;

    @Value("${com.vzw.services.cassandra.GLMarketLegalEntityEndDate}")
    private String glmarketlegalentityenddate;

    @Value("${com.vzw.services.cassandra.GLMarketMapType}")
    private String glmarketmaptype;

    @Value("${com.vzw.services.cassandra.GLMarketEndDate}")
    private String glmarketenddate;

    @Value("${com.vzw.services.cassandra.AlternateBookingType}")
    private String alternatebookingtype;

    @Value("${com.vzw.services.cassandra.FinancialEventCategoryTable}")
    private String financialEventCategoryTable;

    @Value("${com.vzw.services.cassandra.FinancialMarketTable}")
    private String financialMarketTable;

    @Value("${com.vzw.services.cassandra.ProductTable}")
    private String productTable;

    @Value("${com.vzw.services.cassandra.DataEventTable}")
    private String dataEventTable;

    @Value("${com.vzw.services.cassandra.WholesalePriceTable}")
    private String WholesalePriceTable;

    static Logger logger = LoggerFactory.getLogger(CassandraQueryManager.class);

    private static final String CASSANDRA_KEYSPACE = "j6_dev";

    /**
     * Jaja: why did I have to add this ???
     *
     * @return
     */
    public static Session getCassandraSession() {
        if (session == null) {
            AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
            Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();
            session = cluster.connect(CASSANDRA_KEYSPACE);
        }
        return session;
    }

    /**
     * Returns matching rows, output List<FinancialMarket> list. If there are
     * multiple rows returned, send an error code to the program.
     * <p>
     * Cassandra Table Name=FinancialMarket
     *
     * @param session
     * @param file2financialmarketid
     * @return List<FinancialMarket>
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    public List<FinancialMarket> getFinancialMarketRecord(Session session, String file2financialmarketid)
            throws CassandraQueryException, NoResultsReturnedException, MultipleRowsReturnedException {
        List<FinancialMarket> fms = new ArrayList<>();
        String cql_select = "SELECT" + " *" + " FROM financialmarket"
                + " WHERE financialmarketid=? AND fcccgsamapenddate=? "
                + " AND financialmarketmapenddate=? AND glmarketlegalentityenddate=? AND glmarketmaptype=? "
                + " AND glmarketenddate=?  AND alternatebookingtype IN ('Y','N')" + " ALLOW FILTERING";

        System.out.println(cql_select);
        PreparedStatement preparedStatement = session.prepare(cql_select);
        BoundStatement statement = new BoundStatement(preparedStatement);
        statement.bind(file2financialmarketid, fcccgsamapenddate, financialmarketmapenddate, glmarketlegalentityenddate,
                glmarketmaptype, glmarketenddate);
        statement.enableTracing();
        try {
            Result<FinancialMarket> result = new FinancialMarketCassandraMapper().executeAndMapResults(session,
                    statement, new MappingManager(session), false);
            fms = result.all();
        } catch (NoHostAvailableException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Host not available", e);
        } catch (QueryExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query execution exception", e);
        } catch (QueryValidationException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query validation exception", e);
        } catch (UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Unsupported feature", e);
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Null pointer exception", e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Interrupted exception", e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Execution exception", e);
        }

        if (fms.isEmpty()) {
            logger.info("Error message:" + ErrorEnum.NO_ROWS);
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (fms.size() >= 2) {
            logger.info("Error message:" + ErrorEnum.MULTIPLE_ROWS);
            logger.info("Rows returned:" + fms.toString());
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS,
                    " rows returned: " + Integer.toString(fms.size()));
        }

        return fms;
    }

    /**
     * Check if the product is Wholesale product or not. Retrieve matching row,
     * output character 'Y' or 'N'
     * <p>
     * Cassandra Table Name=Product
     *
     * @param session
     * @param TmpProdId
     * @return char
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    public char isWholesaleProduct(Session session, Integer TmpProdId) throws CassandraQueryException {
        char isWholesaleProduct;
        String cql_select = "SELECT " + "*" + " FROM product WHERE productid=?" + " ALLOW FILTERING";
        List<Product> listoffep = new ArrayList<>();
        PreparedStatement preparedStatement = session.prepare(cql_select);
        BoundStatement statement = new BoundStatement(preparedStatement);
        statement.bind(TmpProdId);
        try {
            Result<Product> result = new ProductCassandraMapper().executeAndMapResults(session, statement,
                    new MappingManager(session), false);
            listoffep = result.all();
        } catch (NoHostAvailableException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Host not available", e);
        } catch (QueryExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query execution exception", e);
        } catch (QueryValidationException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query validation exception", e);
        } catch (UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Unsupported feature", e);
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Null pointer exception", e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Interrupted exception", e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Execution exception", e);
        }

        if (listoffep.size() == 1) {
            isWholesaleProduct = listoffep.get(0).getWholesalebillingcode().charAt(0);
        } else {
            isWholesaleProduct = 'N';
        }
        return isWholesaleProduct;

    }

    /**
     * Returns list of FinancialEventCategory records
     * <p>
     * Cassandra Table Name=FinancialEventCategory
     *
     * @param session
     * @param TmpProdId
     * @param File2FinancialMarketId
     * @param InterExchangeCarrierCode
     * @param homesidequalsservingsidindicator
     * @param alternatebookingindicator
     * @return List<FinancialEventCategory>
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    public List<FinancialEventCategory> getFinancialEventCategoryRecord(Session session, Integer TmpProdId,
            String File2FinancialMarketId, Integer InterExchangeCarrierCode, String homesidequalsservingsidindicator,
            String alternatebookingindicator)
            throws MultipleRowsReturnedException, NoResultsReturnedException, CassandraQueryException {
        
        List<FinancialEventCategory> listoffec = new ArrayList<>();
        String cql_selectTest = "SELECT " + "*" + " FROM financialeventcategory"
                + " WHERE productid=? AND homesidequalsservingsidindicator=? AND alternatebookingindicator=? "
                + "  AND (financialmarketid, interexchangecarriercode) = (?, ?)  ALLOW FILTERING";

        PreparedStatement preparedStatement = session.prepare(cql_selectTest);
        BoundStatement statement = new BoundStatement(preparedStatement);
        statement.bind(TmpProdId, homesidequalsservingsidindicator, alternatebookingindicator, File2FinancialMarketId,
                InterExchangeCarrierCode);
        try {
            Result<FinancialEventCategory> result = new FinancialEventCategoryCassandraMapper()
                    .executeAndMapResults(session, statement, new MappingManager(session), false);
            listoffec = result.all();
        } catch (NoHostAvailableException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Host not available", e);
        } catch (QueryExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query execution exception", e);
        } catch (QueryValidationException e) {
            e.printStackTrace();
            throw new CassandraQueryException("Query validation exception", e);
        } catch (UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Unsupported feature", e);
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Null pointer exception", e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Interrupted exception", e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Execution exception", e);
        }

        if (listoffec.isEmpty()) {
            logger.info("Error message:" + ErrorEnum.NO_ROWS);
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (listoffec.size() >= 2) {
            logger.info("Error message:" + ErrorEnum.MULTIPLE_ROWS);
            logger.info("Rows returned:" + listoffec.toString());
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS,
                    " rows returned: " + Integer.toString(listoffec.size()));
        }
        return listoffec;
    }

    /**
     * Returns first record of the List<DataEvent>
     * <p>
     * Cassandra Table Name=DataEvent
     *
     * @param session
     * @param productid
     * @return DataEvent
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    public List<DataEvent> getDataEventRecords(Session session, Integer productid)
            throws MultipleRowsReturnedException, CassandraQueryException, NoResultsReturnedException {
        
        List<DataEvent> listofde = new ArrayList<>();
        String cql_select = "SELECT *  FROM dataevent WHERE productid=? ALLOW FILTERING";
        PreparedStatement preparedStatement = session.prepare(cql_select);
        BoundStatement statement = new BoundStatement(preparedStatement);
        statement.bind(productid);
        try {
            Result<DataEvent> result = new DataEventCassandraMapper().executeAndMapResults(session, statement,
                    new MappingManager(session), false);
            listofde = result.all();
        } catch (NoHostAvailableException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Host not available", e);
        } catch (QueryExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query execution exception", e);
        } catch (QueryValidationException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query validation exception", e);
        } catch (UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Unsupported feature", e);
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Null pointer exception", e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Interrupted exception", e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Execution exception", e);
        }

        if (listofde.isEmpty()) {
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        return listofde;
    }

    /**
     * Returns first record of the WholesalePrice object/one record
     * <p>
     * Cassandra Table Name=WholesalePrice
     *
     * @param session
     * @param productid
     * @param homesidbid
     * @return WholesalePrice
     * @throws CassandraQueryException
     * @throws NoResultsReturnedException
     * @throws MultipleRowsReturnedException
     */
    public List<WholesalePrice> getWholesalePriceRecords(Session session, Integer productid, String homesidbid)
            throws MultipleRowsReturnedException, CassandraQueryException, NoResultsReturnedException {
        
        List<WholesalePrice> listofwp = new ArrayList<>();
        String cql_select = "SELECT * FROM WholesalePrice WHERE productid=? AND homesidbid=? AND servesidbid=?";
        PreparedStatement preparedStatement = session.prepare(cql_select);
        BoundStatement statement = new BoundStatement(preparedStatement);
        statement.bind(productid, homesidbid, "0");
        try {
            Result<WholesalePrice> result = new WholesalePriceCassandraMapper().executeAndMapResults(session, statement,
                    new MappingManager(session), false);
            listofwp = result.all();
        } catch (NoHostAvailableException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Host not available", e);
        } catch (QueryExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query execution exception", e);
        } catch (QueryValidationException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Query validation exception", e);
        } catch (UnsupportedFeatureException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Unsupported feature", e);
        } catch (NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Null pointer exception", e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Interrupted exception", e);
        } catch (ExecutionException e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new CassandraQueryException("Execution exception", e);
        }

        if (listofwp.isEmpty()) {
            logger.info("Error message:" + ErrorEnum.NO_ROWS);
            throw new NoResultsReturnedException(ErrorEnum.NO_ROWS);
        }
        if (listofwp.size() >= 2) {
            logger.info("Error message:" + ErrorEnum.MULTIPLE_ROWS);
            logger.info("Rows returned:" + listofwp.toString());
            throw new MultipleRowsReturnedException(ErrorEnum.MULTIPLE_ROWS,
                    " rows returned: " + Integer.toString(listofwp.size()));
        }
        return listofwp;
    }

    }
