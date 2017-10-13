/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.constants;

/**
 *
 * @author smorcja
 */
public class Constants {
    
//    @Value("${cassandra.db.ip}")
//    public static String CASSANDRA_HOST;
//    
//    @Value("${cassandra.db.username}")
//    public static String CASSANDRA_USERNAME;
//    
//    @Value("${cassandra.db.password}")
//    public static String CASSANDRA_PASSWORD;
//    
//    @Value("${cassandra.db.password.enc}")
//    public static String CASSANDRA_PASSWORD_ENC;
//    
//    @Value("${cassandra.db.keyspace}")
//    public static String CASSANDRA_KEYSPACE;
    
    public static final String BOOK_DATE_FILENAME = "bookdate.csv";
    public static final String FINANCIAL_EVENT_OFFSET_FILENAME = "offset.csv";
    public static final String BILLED_BOOKING_FILENAME = "billed.csv";    
    public static final String UNBILLED_BOOKING_FILENAME = "unbilled.csv";
    public static final String ADMIN_FEES_FILENAME = "adminfees.csv";
    
    public static final String SUBLEDGER_SUMMARY_FILENAME = "subledger_summary.csv";
    public static final String WHOLESALE_REPORT_FILENAME = "wholesale_report.csv";
    
    public static final String DEFAULT_CSV_FIELDS_DELIMITER = ",";
    public static final Integer DEFAULT_MAX_SKIPPED_RECORDS = 100;
    
    public static final String DEBIT_CODE = "DR";
    public static final String CREDIT_CODE = "CR";
    
    public static final String ZERO_CHARGES = "zero";
    public static final String GAPS ="gaps";
    public static final String DATA_ERRORS = "error";
    public static final String BYPASS = "bypass";
    public static final String SUBLEDGER = "subledger";
    public static final String WHOLESALES_REPORT = "report";
}
