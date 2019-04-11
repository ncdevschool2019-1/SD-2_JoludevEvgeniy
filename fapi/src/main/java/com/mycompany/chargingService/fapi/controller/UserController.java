package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = this.userService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersLogin(@RequestBody User user) {
        User updatableUser = this.userService.updateUsersLogin(user.getId(), user.getLogin());
        if (updatableUser != null) {
            return ResponseEntity.ok(updatableUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersEmail(@RequestBody User user) {
        User updatableUser = this.userService.updateUsersEmail(user.getId(), user.getEmail());
        if (updatableUser != null) {
            return ResponseEntity.ok(updatableUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersPassword(@RequestBody UserChangePasswordModel userChangePasswordModel) {
        User updatableUser = this.userService.updateUsersPassword(userChangePasswordModel);
        if (updatableUser != null) {
            return ResponseEntity.ok(updatableUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ResponseEntity<User> getLoginUser(@RequestBody User user) {
        User loginUser = this.userService.getLoginUser(user.getLogin(), user.getPassword());
        if (loginUser != null) {
            return ResponseEntity.ok(loginUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> uploadImage(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) {
        Iterator<String> itr = request.getFileNames();
        User user = this.userService.uploadImage(request.getFile(itr.next()), id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
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
