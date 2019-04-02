package com.mycompany.chargingService.backend.repository;

import com.mycompany.chargingService.backend.entity.Subscript;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptRepository extends CrudRepository<Subscript, Long> {
}
