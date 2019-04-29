package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.AuthToken;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.security.TokenProvider;
import com.mycompany.chargingService.fapi.service.AuthTokenService;
import com.mycompany.chargingService.fapi.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserViewModel userViewModel){
        String answer = this.userValidator.authorizationValidation(userViewModel.getLogin(), userViewModel.getPassword());
        if(!answer.equals("Ok")){
            return ResponseEntity.badRequest().body(answer);
        }
        return ResponseEntity.ok(this.authTokenService.generateAuthToken(userViewModel));
    }



}
