package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.UserViewModel;

import java.util.List;

public interface ValidationService {

    String authorizationValidation(String login, String password);

    String registrationValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels);
}
