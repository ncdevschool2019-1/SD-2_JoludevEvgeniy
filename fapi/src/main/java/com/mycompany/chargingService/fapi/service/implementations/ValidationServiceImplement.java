package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationServiceImplement implements ValidationService {

    @Override
    public String authorizationValidation(String login, String password) {
        if (login.length() < 3 || login.length() > 30) {
            return "Длина вашего логина должна быть не менее 3 и не более 30 символов";
        }
        if (password.length() < 3 || password.length() > 30) {
            return "Длина вашего пароля должна быть не менее 3 и не более 30 символов";
        }
        return "Ok";
    }

    @Override
    public String registrationValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels) {
        if(userViewModel.getLogin().length() < 3 || userViewModel.getLogin().length() > 30){
            return "Длина вашего логина должна быть не менее 3 и не более 30 символов";
        }
        if(userViewModel.getPassword().length() < 3 || userViewModel.getPassword().length() > 30){
            return "Длина вашего пароля должна быть не менее 3 и не более 30 символов";
        }
        if(userViewModel.getEmail().length() < 5 || userViewModel.getEmail().length() > 50){
            return "Длина вашего e-mail должна быть не менее 5 и не более 50 символов";
        }
        for(UserViewModel value: userViewModels){
            if(value.getLogin().equals(userViewModel.getLogin())){
                return "Пользователь с таким логином уже существует";
            }
        }
        return "Ok";
    }

    @Override
    public String updateUsersLoginValidation(UserViewModel userViewModel, List<UserViewModel> userViewModels) {
        if(userViewModel.getLogin().length() < 3 || userViewModel.getLogin().length() > 30){
            return "Длина вашего логина должна быть не менее 3 и не более 30 символов";
        }
        for(UserViewModel value: userViewModels){
            if(value.getLogin().equals(userViewModel.getLogin())){
                return "Пользователь с таким логином уже существует";
            }
        }
        return "Ok";
    }

    @Override
    public String updateUsersPasswordValidation(UserChangePasswordModel userChangePasswordModel, UserViewModel userViewModel) {
        if(userChangePasswordModel.getOldPassword().length() < 3 || userChangePasswordModel.getOldPassword().length() > 30 ||
                userChangePasswordModel.getNewPassword().length() < 3 || userChangePasswordModel.getNewPassword().length() > 30){
            return "Длина вашего пароля должна быть не менее 3 и не более 30 символов";
        }
        if(userChangePasswordModel.getOldPassword().equals(userChangePasswordModel.getNewPassword())){
            return "Ваш новый пароль должен быть другим";
        }
        if(!userChangePasswordModel.getOldPassword().equals(userViewModel.getPassword())){
            return "Вы ввели не правильный ваш ныне существующий пароль";
        }
        return "Ok";
    }

    @Override
    public String updateUsersEmailValidation(UserViewModel userViewModel) {
        if(userViewModel.getEmail().length() < 5 || userViewModel.getEmail().length() > 50){
            return "Длина вашего e-mail должна быть не менее 5 и не более 50 символов";
        }
        return "Ok";
    }

}
