package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.repository.RoleRepository;
import com.mycompany.chargingService.backend.repository.UserRepository;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {

    private UserRepository repository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImplement(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setRole(this.roleRepository.findById(1l).get());
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

    @Override
    public Optional<User> updateUsersLogin(Long id, String login) {
        this.repository.updateUsersLogin(id, login);
        return this.repository.findById(id);
    }

    @Override
    public Optional<User> updateUsersPassword(Long id, String password) {
        this.repository.updateUsersPassword(id, password);
        return this.repository.findById(id);
    }

    @Override
    public Optional<User> updateUsersEmail(Long id, String email) {
        this.repository.updateUsersEmail(id, email);
        return this.repository.findById(id);
    }

    @Override
    public User getLoginUser(String login, String password) {
        return this.repository.getLoginUser(login, password);
    }


}
