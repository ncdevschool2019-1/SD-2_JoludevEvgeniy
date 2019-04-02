package com.mycompany.chargingService.backend.repository;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveSubscriptRepository extends CrudRepository<ActiveSubscript, Long> {
}
