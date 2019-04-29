package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.AuthToken;
import com.mycompany.chargingService.fapi.models.UserViewModel;

public interface AuthTokenService {

    AuthToken generateAuthToken(UserViewModel userViewModel);
}
