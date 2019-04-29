package com.mycompany.chargingService.fapi.validators;

import com.mycompany.chargingService.fapi.models.UserChangeModel;
import com.mycompany.chargingService.fapi.models.UserViewModel;

import java.util.List;

public interface UserValidator {

    String authorizationValidation(String login, String password);

    String registrationValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels);

    String updateUsersLoginValidation(UserChangeModel userChangeModel, List<UserViewModel> userViewModels, UserViewModel userViewModel);

    String updateUsersPasswordValidation(UserChangeModel userChangeModel, UserViewModel userViewModel);

    String updateUsersEmailValidation(UserChangeModel userChangeModel, UserViewModel userViewModel);
}
