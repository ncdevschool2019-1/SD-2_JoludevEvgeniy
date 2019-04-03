package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = this.userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public User saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsersLogin(@RequestBody User user) {
        Optional<User> updatableUser = this.userService.updateUsersLogin(user.getId(), user.getLogin());
        if (updatableUser.isPresent()) {
            return ResponseEntity.ok(updatableUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/password",method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsersPassword(@RequestBody User user) {
        Optional<User> updatableUser = this.userService.updateUsersPassword(user.getId(), user.getPassword());
        if (updatableUser.isPresent()) {
            return ResponseEntity.ok(updatableUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/email",method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsersEmail(@RequestBody User user) {
        Optional<User> updatableUser = this.userService.updateUsersEmail(user.getId(), user.getEmail());
        if (updatableUser.isPresent()) {
            return ResponseEntity.ok(updatableUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/{login}/{password}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(name = "login") String login,
                                            @PathVariable(name = "password") String password) {
        return this.userService.getLoginUser(login, password);
    }
}
