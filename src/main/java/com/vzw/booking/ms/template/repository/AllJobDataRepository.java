/**
 * 
 */
package com.vzw.booking.ms.template.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vzw.booking.ms.template.domain.Alljobdata;

/**
 * <h1>AllJobDataRepository</h1> AllJobDataRepository defines the 
 * Spring Data repository for the alljobdata table. It is used to define which
 * methods should be made available. This example also extends a custom repository
 * to show how complex operations can be added to the standard methods available 
 * from the CRUD repository.
 * <p>
 */

public interface AllJobDataRepository extends CrudRepository<Alljobdata, Long>, AllJobDataRepositoryAugmented {
	/**
	 * Defines a method to search by jobName equals.
	 * @param jobName
	 * @return
	 */
	List<Alljobdata> findByJobname(String jobName);
	/**
	 * Defines a method to search by jobName LIKE.
	 * @param jobName
	 * @return
	 */
	List<Alljobdata> findByJobnameLike(String jobName);
	
}
