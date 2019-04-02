package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Iterable<User> getAllUsers();
    void deleteUser(Long id);

}
