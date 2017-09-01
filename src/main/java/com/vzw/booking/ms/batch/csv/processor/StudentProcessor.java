/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.processor;

import com.vzw.booking.ms.batch.domain.CustomerDTO;
import com.vzw.booking.ms.batch.domain.StudentDTO;
import com.vzw.booking.ms.batch.domain.UserDTO;
import com.vzw.booking.ms.batch.util.CustomerIdGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class StudentProcessor implements ItemProcessor<StudentDTO, UserDTO> {

//    @Autowired
//    private VlfLogger logger;
    
    @Autowired
    public CustomerIdGenerator idGenerator;

    @Override
    public UserDTO process(StudentDTO student) throws Exception {
        //this.logger.write("Processig data", "OUT", VlfLogger.Severity.INFO, "Processing student information: " + item, VlfLogger.LogLevel.NONE);        
        //LOGGER.info("Processing student information: {}", item);

        //this.prettyPrint(student);
        
//        CustomerDTO customer = new CustomerDTO();
//        customer.setCustomerId(idGenerator.generateId());
//        customer.setDiscountCode("N");
//        customer.setName(student.getStudentName());
//        customer.setZip("48124");
//        customer.setEmail(student.getEmailAddress());
//        return customer;

        UserDTO user = new UserDTO();
        user.setUserid(idGenerator.generateId());
        user.setName(student.getStudentName());
        return user;
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
