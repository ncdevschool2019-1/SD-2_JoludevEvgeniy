package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);
}
