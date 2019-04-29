package com.mycompany.chargingService.fapi.validators.impl;

import com.mycompany.chargingService.fapi.models.UserChangeModel;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserValidatorImpl implements UserValidator {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String authorizationValidation(String login, String password) {
        if (login.length() < 3 || login.length() > 30) {
            return "Length of your login should be between 3 and 30 symbols";
        }
        if (password.length() < 3 || password.length() > 30) {
            return "Length of your password should be between 3 and 30 symbols";
        }
        return "Ok";
    }

    @Override
    public String registrationValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels) {
        if(userViewModel.getLogin().length() < 3 || userViewModel.getLogin().length() > 30){
            return "Length of your login should be between 3 and 30 symbols";
        }
        if(userViewModel.getPassword().length() < 3 || userViewModel.getPassword().length() > 30){
            return "Length of your password should be between 3 and 30 symbols";
        }
        if(userViewModel.getEmail().length() < 5 || userViewModel.getEmail().length() > 50){
            return "Length of your e-mail should be between 5 and 50 symbols";
        }
        for(UserViewModel value: userViewModels){
            if(value.getLogin().equals(userViewModel.getLogin())){
                return "User with the same login already exists";
            }
        }
        return "Ok";
    }

    @Override
    public String updateUsersLoginValidation(UserChangeModel userChangeModel, List<UserViewModel> userViewModels, UserViewModel userViewModel) {
        if(!bCryptPasswordEncoder.matches(userChangeModel.getOldPassword(), userViewModel.getPassword())){
            return "You input incorrect password";
        }
        if(userChangeModel.getNewLogin().length() < 3 || userChangeModel.getNewLogin().length() > 30){
            return "Length of your login should be between 3 and 30 symbols";
        }
        for(UserViewModel value: userViewModels){
            if(value.getLogin().equals(userChangeModel.getNewLogin())){
                return "User with the same login already exists";
            }
        }
        return "Ok";
    }

    @Override
    public String updateUsersPasswordValidation(UserChangeModel userChangeModel, UserViewModel userViewModel) {
        if(userChangeModel.getOldPassword().length() < 3 || userChangeModel.getOldPassword().length() > 30 ||
                userChangeModel.getNewPassword().length() < 3 || userChangeModel.getNewPassword().length() > 30){
            return "Length of your password should be between 3 and 30 symbols";
        }
        if(userChangeModel.getOldPassword().equals(userChangeModel.getNewPassword())){
            return "Your new password should be different";
        }
        if(!bCryptPasswordEncoder.matches(userChangeModel.getOldPassword(), userViewModel.getPassword())){
            return "You input incorrect password";
        }
        return "Ok";
    }

    @Override
    public String updateUsersEmailValidation(UserChangeModel userChangeModel, UserViewModel userViewModel) {
        if(!bCryptPasswordEncoder.matches(userChangeModel.getOldPassword(), userViewModel.getPassword())){
            return "You input incorrect password";
        }
        if(userChangeModel.getNewEmail().length() < 5 || userChangeModel.getNewEmail().length() > 50){
            return "Length of your e-mail should between 5 and 50 symbols";
        }
        return "Ok";
    }

}
