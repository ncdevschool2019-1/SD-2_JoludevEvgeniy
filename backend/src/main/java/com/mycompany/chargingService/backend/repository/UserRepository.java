package com.mycompany.chargingService.backend.repository;

import com.mycompany.chargingService.backend.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Transactional
    @Modifying
    @Query(value = "update users set login = :login where id = :id", nativeQuery = true)
    void updateUsersLogin(@Param(value = "id") Long id, @Param(value = "login") String login);

    @Transactional
    @Modifying
    @Query(value = "update users set password = :password where id = :id", nativeQuery = true)
    void updateUsersPassword(@Param(value = "id") Long id, @Param(value = "password") String password);

    @Transactional
    @Modifying
    @Query(value = "update users set email = :email where id = :id", nativeQuery = true)
    void updateUsersEmail(@Param(value = "id") Long id, @Param(value = "email") String email);

    @Query(value = "select * from users where login = :login", nativeQuery = true)
    User getLoginUser(@Param(value = "login")String login);
}
