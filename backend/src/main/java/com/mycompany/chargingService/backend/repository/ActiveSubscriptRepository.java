package com.mycompany.chargingService.backend.repository;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ActiveSubscriptRepository extends CrudRepository<ActiveSubscript, Long> {

    @Transactional
    @Modifying
    @Query(value = "update active_subscripts set last_write_off = now() where id = :id", nativeQuery = true)
    void setTimeNow(@Param(value = "id") Long id);

    @Query(value = "select timestampdiff(minute, last_write_off, now()) from active_subscripts where id = :id", nativeQuery = true)
    int getTimesDifference(@Param(value = "id") Long id);

}
