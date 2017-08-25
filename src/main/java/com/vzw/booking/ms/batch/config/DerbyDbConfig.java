/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.config;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.ClientDataSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class DerbyDbConfig {

    /**
     * native data source creation - doesn't work
     *
     * @param database
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static DataSource getNativeDS(String database, String user, String password) throws SQLException {
        ClientDataSource ds = new ClientDataSource();
        ds.setDatabaseName(database);
        ds.setUser(user);
        ds.setPassword(password);
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        return ds;
    }

    /**
     * apache basic data source for migration
     *
     * @param database
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static DataSource getBasicDS(String user, String password) throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        ds.setUrl("jdbc:derby://localhost:1527/sample");
        ds.setUsername("app");
        ds.setPassword("app");
        //ds.set
        return ds;
    }

    Properties hibernateProperties() {
        return new Properties() {
            private static final long serialVersionUID = 1L;
            {
                setProperty("hibernate.hbm2ddl.auto", "create");
                setProperty("hibernate.show_sql", "false");
                setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
            }
        };
    }
    
//    /**
//     *
//     * @return
//     */
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() throws SQLException {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(getBasicDS("sample", "app", "app"));
//        //sessionFactory.setPackagesToScan(new String[]{"com.spring.app.model"});
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }

//    @Bean
//    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
//         HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
//         factory.setEntityManagerFactory(emf);
//         return factory;
//    }
}
