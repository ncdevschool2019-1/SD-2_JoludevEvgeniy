package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {

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
    public String updateUsersLoginValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels) {
        if(userViewModel.getLogin().length() < 3 || userViewModel.getLogin().length() > 30){
            return "Length of your login should be between 3 and 30 symbols";
        }
        for(UserViewModel value: userViewModels){
            if(value.getLogin().equals(userViewModel.getLogin())){
                return "User with the same login already exists";
            }
        }
        return "Ok";
    }

    @Override
    public String updateUsersPasswordValidation(UserChangePasswordModel userChangePasswordModel, UserViewModel userViewModel) {
        if(userChangePasswordModel.getOldPassword().length() < 3 || userChangePasswordModel.getOldPassword().length() > 30 ||
                userChangePasswordModel.getNewPassword().length() < 3 || userChangePasswordModel.getNewPassword().length() > 30){
            return "Length of your password should be between 3 and 30 symbols";
        }
        if(userChangePasswordModel.getOldPassword().equals(userChangePasswordModel.getNewPassword())){
            return "Your new password should be different";
        }
        if(!userChangePasswordModel.getOldPassword().equals(userViewModel.getPassword())){
            return "You input incorrect password";
        }
        return "Ok";
    }

    @Override
    public String updateUsersEmailValidation(UserViewModel userViewModel) {
        if(userViewModel.getEmail().length() < 5 || userViewModel.getEmail().length() > 50){
            return "Length of your e-mail should between 5 and 50 symbols";
        }
        return "Ok";
    }

}
