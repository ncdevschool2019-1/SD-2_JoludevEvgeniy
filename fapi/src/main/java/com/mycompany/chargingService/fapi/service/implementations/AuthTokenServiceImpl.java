package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.AuthToken;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.security.TokenProvider;
import com.mycompany.chargingService.fapi.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public AuthToken generateAuthToken(UserViewModel userViewModel) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userViewModel.getLogin(),
                userViewModel.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new AuthToken(token, userDetails.getUsername(), userDetails.getAuthorities().toArray()[0].toString());
    }
}
