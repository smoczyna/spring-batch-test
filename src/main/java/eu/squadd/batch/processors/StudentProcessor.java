/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.processors;

import eu.squadd.batch.domain.CustomerDTO;
import eu.squadd.batch.domain.StudentDTO;
import eu.squadd.batch.util.CustomerIdGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class StudentProcessor implements ItemProcessor<StudentDTO, CustomerDTO> {

    @Autowired
    public CustomerIdGenerator idGenerator;

    @Override
    public CustomerDTO process(StudentDTO student) throws Exception {
        //this.prettyPrint(student);
        
        CustomerDTO customer = new CustomerDTO();
        customer.setCustomerId(idGenerator.generateId());
        customer.setDiscountCode("N");
        customer.setName(student.getStudentName());
        customer.setZip("48124");
        customer.setEmail(student.getEmailAddress());
        return customer;
    }
    
//    private void prettyPrint(StudentDTO item) {
//        logger.newLogSet();
//        logger.write("", "", Severity.INFO, "*** Processing student record: ***", LogLevel.INFO);
//        logger.write("", "", Severity.INFO, "       student name: " + item.getStudentName(), LogLevel.INFO);
//        logger.write("", "", Severity.INFO, "       student email: " + item.getStudentName(), LogLevel.INFO);
//        logger.write("", "", Severity.INFO, "       student assignment: " + item.getStudentName(), LogLevel.INFO);
//        logger.write("", "", Severity.INFO, "*** end of student record ***", LogLevel.INFO);
//        logger.endLogSet();
//    }
}
