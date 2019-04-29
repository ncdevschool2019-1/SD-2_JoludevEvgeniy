package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    Long getMaxPage();
    Iterable<User> getUsersOnPage(Integer pageNumber);
    User saveUser(User user);
    User getUserById(Long id);
    Iterable<User> getAllUsers();
    void deleteUser(Long id);
    User updateUsersLogin(Long id, String login);
    User updateUsersPassword(Long id, String password);
    User updateUsersEmail(Long id, String email);
    User getLoginUser(String login);
    User uploadUsersImage(MultipartFile image, Long id) throws IOException;
    Resource getImage(String imageName);
    void deleteImage(String imageName);

}
