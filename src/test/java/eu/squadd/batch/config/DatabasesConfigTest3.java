/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class DatabasesConfigTest3 {
    
    /**
     * Test of getSampleDerbyDS method, of class DatabasesConfig.
     * @throws java.lang.Exception
     */
    @Test
    public void testSampleDerbyDS() throws Exception {
        System.out.println("Check internal Derby connection, checking default 'sample' database");
        DataSource result = DatabasesConfig.getSampleDerbyDS();        
        assertNotNull(result);
    }

    /**
     * Test of getCasandraBasicDs method, of class DatabasesConfig.
     * @throws java.lang.Exception
     */
//    @Test
//    public void testGetCasandraBasicDs() throws Exception {
//        System.out.println("Check Casandra DEV space connection");
//        String user = "j6_dev_user";
//        String password = "Ireland";
//        DataSource result = DatabasesConfig.getCasandraBasicDs(user, password);
//        assertNotNull(result);
//    }
    
    //@Test
    public void testCasandraConnectivity() throws Exception {
        System.out.println("Check Casandra native connectivity using Datastax driver");
        //Session session = DatabasesConfig.getCasandraSession();
        AuthProvider authProvider = new PlainTextAuthProvider("j6_dev_user", "Ireland");
        Cluster cluster = Cluster.builder().addContactPoint("170.127.114.154").withAuthProvider(authProvider).build();    
        assertNotNull(cluster);
        Metadata meta = cluster.getMetadata();
        List<KeyspaceMetadata> spaces = meta.getKeyspaces();
        assertNotNull(spaces);
        System.out.println("Keyspaces found: "+spaces.size());
        spaces.forEach((keyspace) -> {
            System.out.println("    "+keyspace.getName());
        });
        Session session = cluster.connect("j6_dev");
        assertNotNull(session); 
    }

    //@Test
    public void testSystemSchemaAccess() throws Exception {
        Session session = DatabasesConfig.getCasandraSession("system_schema");
        assertNotNull(session);
        
        ResultSet result = session.execute("select * from tables");
        assertTrue(result.all().size()>0);
        System.out.println("Tables found: "+result.all().size());
        for (Row row : result) {
            System.out.println(row.getString("table_name"));
        }
    }
    
    //@Test
    public void testCassandraUserTablesAccess() throws Exception {
        Session session = DatabasesConfig.getCasandraSession("j6_dev");
        assertNotNull(session);
        
        ResultSet result1 = session.execute("select * from users_test");
        int userCount = result1.all().size();
        System.out.println("Number of users found: "+userCount);
        assertTrue(userCount>0);
        
//        session.execute("insert into users_test(userid, name) values(1, 'test user')");
//        ResultSet result2 = session.execute("select * from users_test");
//        int userCount2 = result2.all().size();
//        System.out.println("Users found in second call: "+userCount2);
//        assertTrue(userCount2==userCount+1);
//        
//        session.execute("delete from users_test where userid = 1");
//        ResultSet result3 = session.execute("select * from users_test");
//        int userCount3 = result3.all().size();
//        System.out.println("Users found in third call: "+userCount3);
//        assertEquals(userCount, userCount3);        
    }
    
    //@Test
    public void testFinancialMarketTable() throws Exception {
        Session session = DatabasesConfig.getCasandraSession("j6_dev");
        assertNotNull(session);
        
        ResultSet result2 = session.execute("select * from financialmarket");
        List<ColumnDefinitions.Definition> cols = result2.getColumnDefinitions().asList();
        assertNotNull(cols);
        assertEquals(22, cols.size());
        System.out.println("Financial market table structure:");
        for (Definition def : cols)
            System.out.println("Column: " + def.getName());
        
        //MappingManager manager = new MappingManager(session);
        //Mapper<FinancialMarket> mapper = manager.mapper(FinancialMarket.class);
        //FinancialMarket market = mapper.get(""); //get’s arguments must match the partition key components (number of arguments and their types).
    }
   
}
