/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.writers;

import com.datastax.driver.core.Session;
import com.vzw.booking.ms.batch.config.DatabasesConfig;
import com.vzw.booking.ms.batch.domain.UserDTO;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

/**
 *
 * @author smoczyna
 */
public class CasandraDbWriter implements ItemWriter<UserDTO> {

    private String statement = "insert into users_test(usereid, name) values (%i, '%s')";
    private Session casandraSession = DatabasesConfig.getCasandraSession("j6_dev");

    @Override
    public void write(List<? extends UserDTO> list) throws Exception {
        list.forEach((user) -> {
            casandraSession.execute(String.format(statement, user.getUserid(), user.getName()));
        });
    }

}
