/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.processors;

import eu.squadd.batch.domain.StudentDTO;
import eu.squadd.batch.domain.UserDTO;
import eu.squadd.batch.util.CustomerIdGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class StudentToUserProcessor implements ItemProcessor<StudentDTO, UserDTO> {
    
    @Autowired
    public CustomerIdGenerator idGenerator;

    @Override
    public UserDTO process(StudentDTO student) throws Exception {
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

