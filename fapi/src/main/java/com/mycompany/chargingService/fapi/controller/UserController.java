package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.service.UserService;
import com.mycompany.chargingService.fapi.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService userService;
    private UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserViewModel> getUserById(@PathVariable(name = "id") Long id) {
        UserViewModel userViewModel = this.userService.getUserById(id);
        if (userViewModel != null) {
            return ResponseEntity.ok(userViewModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/logged", method = RequestMethod.POST)
    public ResponseEntity getLoggedUser(@RequestBody String login){
        UserViewModel userViewModel = this.userService.getLoginUser(login);
        if(userViewModel != null){
            return ResponseEntity.ok(userViewModel);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody UserViewModel userViewModel) {
        String answer = this.userValidator.registrationValidation(userViewModel, this.userService.getAllUsers());
        if (answer.equals("Ok")) {
            UserViewModel savedUserViewModel = this.userService.saveUser(userViewModel);
            if (savedUserViewModel != null) {
                return ResponseEntity.ok(savedUserViewModel);
            }
        }
        return ResponseEntity.badRequest().body(answer);
    }

    @PreAuthorize("hasRole('Admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity updateUsersLogin(@RequestBody UserViewModel userViewModel) {
        String answer = this.userValidator.updateUsersLoginValidation(userViewModel, this.userService.getAllUsers());
        if (answer.equals("Ok")) {
            UserViewModel updatableUserViewModel = this.userService.updateUsersLogin(userViewModel);
            if (updatableUserViewModel != null) {
                return ResponseEntity.ok(updatableUserViewModel);
            }
        }
        return ResponseEntity.badRequest().body(answer);
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResponseEntity updateUsersEmail(@RequestBody UserViewModel userViewModel) {
        String answer = this.userValidator.updateUsersEmailValidation(userViewModel);
        if (answer.equals("Ok")) {
            UserViewModel updatableUserViewModel = this.userService.updateUsersEmail(userViewModel);
            if (updatableUserViewModel != null) {
                return ResponseEntity.ok(updatableUserViewModel);
            }
        }
        return ResponseEntity.badRequest().body(answer);
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ResponseEntity updateUsersPassword(@RequestBody UserChangePasswordModel
                                                      userChangePasswordModel) {
        String answer = this.userValidator.updateUsersPasswordValidation(userChangePasswordModel,
                this.userService.getUserById(userChangePasswordModel.getUserId()));
        if (answer.equals("Ok")) {
            UserViewModel updatableUserViewModel = this.userService.updateUsersPassword(userChangePasswordModel);
            if (updatableUserViewModel != null) {
                return ResponseEntity.ok(updatableUserViewModel);
            }
        }
        return ResponseEntity.badRequest().body(answer);
    }


    @PreAuthorize("hasRole('User') or hasRole('Admin')")
    @RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<UserViewModel> uploadImage(MultipartHttpServletRequest
                                                             request, @PathVariable(name = "id") Long id) {
        Iterator<String> itr = request.getFileNames();
        UserViewModel userViewModel = this.userService.uploadImage(request.getFile(itr.next()), id);
        if (userViewModel != null) {
            return ResponseEntity.ok(userViewModel);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('User') or hasRole('Admin')")
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
