package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.models.UserChangeModel;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    Long getMaxPage();

    List<UserViewModel> getAllUsers();

    List<UserViewModel> getUsersOnPage(Integer pageNumber);

    UserViewModel getUserById(Long id);

    UserViewModel saveUser(UserViewModel userViewModel);

    void deleteUser(Long id);

    UserViewModel updateUsersLogin(UserChangeModel userChangeModel);

    UserViewModel updateUsersEmail(UserChangeModel userChangeModel);

    UserViewModel updateUsersPassword(UserChangeModel userChangeModel);

    UserViewModel getLoginUser(String login);

    UserViewModel uploadImage(MultipartFile image, Long id);

    Resource getImage(String imageName);

}
