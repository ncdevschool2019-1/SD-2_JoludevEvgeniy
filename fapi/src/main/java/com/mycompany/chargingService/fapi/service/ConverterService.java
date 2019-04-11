package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserViewModel;

public interface ConverterService {

    UserViewModel convertUserToUserViewModel(User user);
}
