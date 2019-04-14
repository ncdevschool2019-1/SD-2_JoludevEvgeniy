package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.models.UserViewModel;

import java.util.List;

public interface ValidationService {

    String authorizationValidation(String login, String password);

    String registrationValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels);

    String updateUsersLoginValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels);

    String updateUsersPasswordValidation(UserChangePasswordModel userChangePasswordModel, UserViewModel userViewModel);

    String updateUsersEmailValidation(UserViewModel userViewModel);
}
