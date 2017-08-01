package com.vzw.booking.ms.template.repository;

import java.util.List;

import com.vzw.booking.ms.template.domain.Alljobdata;

/**
 * <h1>AllJobDataRepositoryAugmented</h1> AllJobDataRepositoryAugmented augments the standard
 * CRUD repository. It provides a way to add custom access methods
 *  to the repository. In this example, it adds a method to query by stored procedure.
 * <p>
 * 
 */
public interface AllJobDataRepositoryAugmented {

	List<Alljobdata> findByJobnameSP(String jobName);
}
