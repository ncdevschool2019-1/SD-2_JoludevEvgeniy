package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<UserViewModel> getAllUsers();

    UserViewModel getUserById(Long id);

    UserViewModel saveUser(UserViewModel userViewModel);

    void deleteUser(Long id);

    UserViewModel updateUsersLogin(UserViewModel userViewModel);

    UserViewModel updateUsersEmail(UserViewModel userViewModel);

    UserViewModel updateUsersPassword(UserChangePasswordModel userChangePasswordModel);

    UserViewModel getLoginUser(String login, String password);

    UserViewModel uploadImage(MultipartFile image, Long id);

    Resource getImage(String imageName);

}
