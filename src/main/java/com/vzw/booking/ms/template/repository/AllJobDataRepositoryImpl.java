package com.vzw.booking.ms.template.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vzw.booking.ms.template.domain.Alljobdata;

/**
 * <h1>AllJobDataRepositoryImpl</h1> AllJobDataRepositoryImpl is the implementation of the
 * AllJobDataRepositoryAugmented interface. This class adds the custom
 * access method to query using a stored procedure.
 * <p>
 */
public class AllJobDataRepositoryImpl implements AllJobDataRepositoryAugmented {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * This method uses a JdbcTemplate to execute a stored procedure. It uses the 
	 * BeanPropertyRowMapper class to map the columns from the query results into
	 * the domain object. The result is the call to the database returns a list
	 * of the domain objects.
	 */
	public List<Alljobdata> findByJobnameSP(String jobName) {
		List<Alljobdata> results;
		results = jdbcTemplate.query("CALL sps_GetByJobname(?);",
				new BeanPropertyRowMapper<Alljobdata>(Alljobdata.class),
				jobName);
		return results;
	}

}
