package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.service.UserService;
import com.mycompany.chargingService.fapi.service.ValidationService;
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
    private ValidationService validationService;

    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserViewModel> getUserById(@PathVariable(name = "id") Long id) {
        UserViewModel userViewModel = this.userService.getUserById(id);
        if (userViewModel != null) {
            return ResponseEntity.ok(userViewModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> saveUser(@RequestBody UserViewModel userViewModel) {
        String answer = this.validationService.registrationValidation(userViewModel, this.userService.getAllUsers());
        if (answer.equals("Ok")) {
            UserViewModel savedUserViewModel = this.userService.saveUser(userViewModel);
            if (savedUserViewModel != null) {
                return ResponseEntity.ok(savedUserViewModel);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> updateUsersLogin(@RequestBody UserViewModel userViewModel) {
        UserViewModel updatableUserViewModel = this.userService.updateUsersLogin(userViewModel.getId(), userViewModel.getLogin());
        if (updatableUserViewModel != null) {
            return ResponseEntity.ok(updatableUserViewModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> updateUsersEmail(@RequestBody UserViewModel userViewModel) {
        UserViewModel updatableUserViewModel = this.userService.updateUsersEmail(userViewModel.getId(), userViewModel.getEmail());
        if (updatableUserViewModel != null) {
            return ResponseEntity.ok(updatableUserViewModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> updateUsersPassword(@RequestBody UserChangePasswordModel userChangePasswordModel) {
        UserViewModel updatableUserViewModel = this.userService.updateUsersPassword(userChangePasswordModel);
        if (updatableUserViewModel != null) {
            return ResponseEntity.ok(updatableUserViewModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> getLoginUser(@RequestBody UserViewModel userViewModel) {
        String answer = this.validationService.authorizationValidation(userViewModel.getLogin(), userViewModel.getPassword());
        if (answer.equals("Ok")) {
            UserViewModel loginUserViewModel = this.userService.getLoginUser(userViewModel.getLogin(), userViewModel.getPassword());
            if (loginUserViewModel != null) {
                return ResponseEntity.ok(loginUserViewModel);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> uploadImage(MultipartHttpServletRequest request, @PathVariable(name = "id") Long id) {
        Iterator<String> itr = request.getFileNames();
        UserViewModel userViewModel = this.userService.uploadImage(request.getFile(itr.next()), id);
        if (userViewModel != null) {
            return ResponseEntity.ok(userViewModel);
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
