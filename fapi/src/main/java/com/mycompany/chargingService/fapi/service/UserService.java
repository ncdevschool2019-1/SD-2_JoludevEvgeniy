package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    void deleteUser(Long id);

    User updateUsersLogin(Long id, String login);

    User updateUsersEmail(Long id, String email);

    User updateUsersPassword(UserChangePasswordModel userChangePasswordModel);

    User getLoginUser(String login, String password);

    User uploadImage(MultipartFile image, Long id);

    Resource getImage(String imageName);

}
