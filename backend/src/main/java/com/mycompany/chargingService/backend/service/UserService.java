package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Iterable<User> getAllUsers();
    void deleteUser(Long id);
    Optional<User> updateUsersLogin(Long id, String login);
    Optional<User> updateUsersPassword(Long id, String password);
    Optional<User> updateUsersEmail(Long id, String email);
    User getLoginUser(String login, String password);
    boolean uploadUsersImage(MultipartFile image, Long id) throws IOException;
    Resource getImage(String imageName);
    void deleteImage(String imageName);

}
