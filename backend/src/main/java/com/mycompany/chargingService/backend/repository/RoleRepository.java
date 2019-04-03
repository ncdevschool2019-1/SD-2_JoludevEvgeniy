package com.mycompany.chargingService.backend.repository;

import com.mycompany.chargingService.backend.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
