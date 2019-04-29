package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.User;
import com.mycompany.chargingService.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        Long id = this.userService.saveUser(user).getId();
        return ResponseEntity.ok(this.userService.getUserById(id));


    }

    @RequestMapping(value = "/page/{pageNumber}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUsersOnPage(@PathVariable(name = "pageNumber") Integer pageNumber){
        return ResponseEntity.ok(this.userService.getUsersOnPage(pageNumber));
    }

    @RequestMapping(value = "/maxPage", method = RequestMethod.GET)
    public ResponseEntity<Long> getMaxPage(){
        return ResponseEntity.ok(this.userService.getMaxPage());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        User user = this.userService.getUserById(id);
        if (user != null) {
            if(user.getImagePath() != null) {
                this.userService.deleteImage(this.userService.getUserById(id).getImagePath());
            }
            this.userService.deleteUser(id);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersLogin(@RequestBody User user) {

        return ResponseEntity.ok(this.userService.updateUsersLogin(user.getId(), user.getLogin()));

    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersPassword(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateUsersPassword(user.getId(), user.getPassword()));

    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity<User> updateUsersEmail(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.updateUsersEmail(user.getId(), user.getEmail()));
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ResponseEntity<User> getUserById(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.getLoginUser(user.getLogin()));

    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> uploadFile(@RequestParam("image")MultipartFile image, @PathVariable(name = "id") Long id) throws IOException {
        return ResponseEntity.ok(this.userService.uploadUsersImage(image, id));
    }

    @RequestMapping(value = "/image/{imageName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Resource image = null;
        try {
            image = this.userService.getImage(imageName);
            if(!image.isReadable()){
                image = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(image);
    }
}
