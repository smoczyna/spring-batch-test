package eu.squadd.batch.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import eu.squadd.batch.utils.AbstractMapper;
import eu.squadd.batch.domain.casandra.FinancialEventCategory;

public class FinancialEventCategoryCassandraMapper extends AbstractMapper<FinancialEventCategory> {

    /* (non-Javadoc)
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.datastax.driver.mapping.MappingManager)
     */
    @Override
    protected Mapper<FinancialEventCategory> getMapper(MappingManager manager) {
        return manager.mapper(FinancialEventCategory.class);
    }

}
