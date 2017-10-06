package eu.squadd.batch.domain.casandra.mappers;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import eu.squadd.batch.utils.AbstractMapper;
import eu.squadd.batch.domain.casandra.WholesalePrice;

public class WholesalePriceCassandraMapper extends AbstractMapper<WholesalePrice> {

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.vzw.services.cassandra.api.model.AbstractMapper#getMapper(com.
	 * datastax.driver.mapping.MappingManager)
     */
    @Override
    protected Mapper<WholesalePrice> getMapper(MappingManager manager) {
        return manager.mapper(WholesalePrice.class);
    }

}
