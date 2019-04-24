package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.AuthToken;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.security.TokenProvider;
import com.mycompany.chargingService.fapi.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserViewModel userViewModel){
        String answer = this.userValidator.authorizationValidation(userViewModel.getLogin(), userViewModel.getPassword());
        if(answer != "Ok"){
            return ResponseEntity.badRequest().body(answer);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userViewModel.getLogin(),
                        userViewModel.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new AuthToken(token, userDetails.getUsername(), userDetails.getAuthorities().toArray()[0].toString()));
    }



}
