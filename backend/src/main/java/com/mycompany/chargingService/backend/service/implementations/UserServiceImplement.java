package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.repository.UserRepository;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImplement(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) {
        return this.repository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        this.repository.deleteById(id);
    }
}
