/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.writers;

import org.springframework.batch.item.ItemWriter;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import eu.squadd.batch.config.DatabasesConfig;
import eu.squadd.batch.domain.UserDTO;
import java.util.List;

/**
 *
 * @author smoczyna
 */
public class CasandraDbWriter implements ItemWriter<UserDTO> {
    
//    private final Session casandraSession;
//    private final PreparedStatement statement;

    public CasandraDbWriter() {
        //this.casandraSession  = DatabasesConfig.getCasandraSession("j6_dev");
        //statement = casandraSession.prepare("insert into users_test(userid, name) values (?, ?)");
    }
    
    @Override
    public void write(List<? extends UserDTO> list) throws Exception {        
        list.forEach((user) -> {
            //BoundStatement boundSt = statement.bind(user.getUserid(), user.getName());
            //casandraSession.execute(boundSt);
        });
    }

}
