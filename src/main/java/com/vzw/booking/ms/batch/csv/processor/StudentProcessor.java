/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.processor;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import com.vzw.booking.ms.batch.domain.StudentDTO;
import com.vzw.booking.ms.batch.util.CustomerIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class StudentProcessor implements ItemProcessor<StudentDTO, CustomerDTO> {

//    @Autowired
//    private VlfLogger logger;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentProcessor.class);
    
    @Autowired
    public CustomerIdGenerator idGenerator;

    @Override
    public CustomerDTO process(StudentDTO student) throws Exception {
        //this.logger.write("Processig data", "OUT", VlfLogger.Severity.INFO, "Processing student information: " + item, VlfLogger.LogLevel.NONE);        
        //LOGGER.info("Processing student information: {}", item);

        this.prettyPrint(student);
        
        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerId(idGenerator.generateId());
        customer.setDiscountCode("N");
        customer.setName(student.getStudentName());
        customer.setZip("48124");
        customer.setEmail(student.getEmailAddress());
        return customer;
    }
    
    private void prettyPrint(StudentDTO item) {
        LOGGER.info("*** Processing student record: ***");
        LOGGER.info("       student name: " + item.getStudentName());
        LOGGER.info("       student email: " + item.getStudentName());
        LOGGER.info("       student assignment: " + item.getStudentName());
        LOGGER.info("*** end of student ***");
    }
}
