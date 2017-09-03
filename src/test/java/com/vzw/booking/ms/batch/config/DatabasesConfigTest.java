/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.config;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
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
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author smorcja
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class DatabasesConfigTest {
    
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
//        Session session = cluster.connect("system_schema");
//        assertNotNull(session);
//        
//        ResultSet result = session.execute("select * from tables");
//        assertTrue(result.all().size()>0);
//        System.out.println("Tables found: "+result.all().size());
//        for (Row row : result) {
//            System.out.println(row.getString("table_name"));
//        }
        
        Session session = cluster.connect("j6_dev");
        assertNotNull(session);
        ResultSet result1 = session.execute("select * from users_test");
        System.out.println("Users foud: "+result1.all().size());
        assertTrue(result1.all().isEmpty());
        
        session.execute("insert into users_test(userid, name) values(1, 'test user')");
        ResultSet result2 = session.execute("select * from users_test");
        System.out.println("USers foud: "+result2.all().size());
        assertTrue(result2.all().size()==1);
        
        session.execute("delete from users_test where userid = 1");
        ResultSet result3 = session.execute("select * from users_test");
        System.out.println("USers foud: "+result3.all().size());
        assertTrue(result3.all().isEmpty());
    }
}
