package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.service.ImageService;
import com.mycompany.chargingService.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    @Value("${backend.server.url}api/users")
    private String backendServerUrl;
    private RestTemplate restTemplate = new RestTemplate();

    private ImageService imageService;

    @Autowired
    public UserServiceImplement(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public List<User> getAllUsers() {
        User[] users = restTemplate.getForEntity(backendServerUrl, User[].class).getBody();
        return users == null ? Collections.emptyList() : Arrays.asList(users);
    }

    @Override
    public User getUserById(Long id) {
        return restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody();
    }

    @Override
    public User saveUser(User user) {
        User[] users = restTemplate.getForEntity(backendServerUrl, User[].class).getBody();
        if (users != null) {
            for (User value : users) {
                if (value.getLogin().equals(user.getLogin())) {
                    return null;
                }
            }
        }
        return restTemplate.postForEntity(backendServerUrl, user, User.class).getBody();
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }

    @Override
    public User updateUsersLogin(Long id, String login) {
        User[] users = restTemplate.getForEntity(backendServerUrl, User[].class).getBody();
        User user = restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody();
        if (user == null) {
            return null;
        }
        if (users != null) {
            for (User value : users) {
                if (value.getLogin().equals(login)) {
                    return null;
                }
            }
        }
        user.setLogin(login);
        return restTemplate.postForEntity(backendServerUrl + "/login", user, User.class).getBody();
    }

    @Override
    public User updateUsersEmail(Long id, String email) {
        User user = restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody();
        if (user != null) {
            user.setEmail(email);
        }
        return restTemplate.postForEntity(backendServerUrl + "/email", user, User.class).getBody();
    }

    @Override
    public User updateUsersPassword(UserChangePasswordModel userChangePasswordModel) {
        User user = restTemplate.getForEntity(backendServerUrl + "/" + userChangePasswordModel.getUserId(), User.class).getBody();
        if (user != null && userChangePasswordModel.getOldPassword().equals(user.getPassword())) {
            user.setPassword(userChangePasswordModel.getNewPassword());
            return restTemplate.postForEntity(backendServerUrl + "/password", user, User.class).getBody();
        } else {
            return null;
        }

    }

    @Override
    public User getLoginUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return restTemplate.postForEntity(backendServerUrl + "/authorization", user, User.class).getBody();
    }

    @Override
    public User uploadImage(MultipartFile image, Long id) {
        return restTemplate.postForEntity(backendServerUrl + "/image/" + id,
                imageService.uploadImage(image), User.class).getBody();
    }

    @Override
    public Resource getImage(String imageName) {
        return restTemplate.getForEntity(backendServerUrl + "/image/" + imageName, Resource.class).getBody();
    }

}
