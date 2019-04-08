package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;
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
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = this.userService.getAllUsers();
        if (users.iterator().hasNext()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Long id = this.userService.saveUser(user).getId();
        Optional<User> savedUser = this.userService.getUserById(id);
        if (savedUser.isPresent()) {
            return ResponseEntity.ok(savedUser.get());
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        if (this.userService.getUserById(id).isPresent()) {
            this.userService.deleteImage(this.userService.getUserById(id).get().getImagePath());
            this.userService.deleteUser(id);
        }
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

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsersPassword(@RequestBody User user) {
        Optional<User> updatableUser = this.userService.updateUsersPassword(user.getId(), user.getPassword());
        if (updatableUser.isPresent()) {
            return ResponseEntity.ok(updatableUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUsersEmail(@RequestBody User user) {
        Optional<User> updatableUser = this.userService.updateUsersEmail(user.getId(), user.getEmail());
        if (updatableUser.isPresent()) {
            return ResponseEntity.ok(updatableUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ResponseEntity<User> getUserById(@RequestBody User user) {
        User authUser = this.userService.getLoginUser(user.getLogin(), user.getPassword());
        if (authUser != null) {
            return ResponseEntity.ok(authUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> uploadFile(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) throws IOException {

        Iterator<String> itr = request.getFileNames();
        if (this.userService.uploadUsersImage(request.getFile(itr.next()), id) &&
                this.userService.getUserById(id).isPresent()) {
            return ResponseEntity.ok(this.userService.getUserById(id).get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Resource image = this.userService.getImage(imageName);
            if (image.exists() || image.isReadable()) {
                return ResponseEntity.ok(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}
