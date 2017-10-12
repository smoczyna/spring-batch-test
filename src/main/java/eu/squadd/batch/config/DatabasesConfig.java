/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.ClientDataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class DatabasesConfig {

    /**
     * native data source creation - doesn't work
     *
     * @param database
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static DataSource getNativeDerbyDS(String database, String user, String password) throws SQLException {
        ClientDataSource ds = new ClientDataSource();
        ds.setDatabaseName(database);
        ds.setUser(user);
        ds.setPassword(password);
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        return ds;
    }

    /**
     * apache basic data source for migration - works fine
     *
     * @return
     * @throws SQLException
     */
    public static DataSource getSampleDerbyDS() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/sample");
        ds.setUsername("app");
        ds.setPassword("app");
        return ds;
    }

    /**
     * Datastax driver way
     * @param keyspace
     * @return 
     */
    public static Session getCasandraSession(String keyspace) {
        AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
        Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();        
        return cluster.connect(keyspace);
    }
}
