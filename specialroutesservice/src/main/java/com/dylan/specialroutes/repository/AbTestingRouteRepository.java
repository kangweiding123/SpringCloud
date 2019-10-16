package com.dylan.specialroutes.repository;

import com.dylan.specialroutes.model.AbTestingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DylanKang
 * @Description:
 * @Date 2019/10/12
 */
@Repository
public interface  AbTestingRouteRepository extends CrudRepository<AbTestingRoute,String> {
    public AbTestingRoute findByServiceName(String serviceName);
}
