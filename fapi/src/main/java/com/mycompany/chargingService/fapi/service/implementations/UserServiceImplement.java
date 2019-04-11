package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.UserViewModel;
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
    public List<UserViewModel> getAllUsers() {
        UserViewModel[] userViewModels = restTemplate.getForEntity(backendServerUrl, UserViewModel[].class).getBody();
        return userViewModels == null ? Collections.emptyList() : Arrays.asList(userViewModels);
    }

    @Override
    public UserViewModel getUserById(Long id) {
        return restTemplate.getForEntity(backendServerUrl + "/" + id, UserViewModel.class).getBody();
    }

    @Override
    public UserViewModel saveUser(UserViewModel userViewModel) {
        UserViewModel[] userViewModels = restTemplate.getForEntity(backendServerUrl, UserViewModel[].class).getBody();
        if (userViewModels != null) {
            for (UserViewModel value : userViewModels) {
                if (value.getLogin().equals(userViewModel.getLogin())) {
                    return null;
                }
            }
        }
        return restTemplate.postForEntity(backendServerUrl, userViewModel, UserViewModel.class).getBody();
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }

    @Override
    public UserViewModel updateUsersLogin(Long id, String login) {
        UserViewModel[] userViewModels = restTemplate.getForEntity(backendServerUrl, UserViewModel[].class).getBody();
        UserViewModel userViewModel = restTemplate.getForEntity(backendServerUrl + "/" + id, UserViewModel.class).getBody();
        if (userViewModel == null) {
            return null;
        }
        if (userViewModels != null) {
            for (UserViewModel value : userViewModels) {
                if (value.getLogin().equals(login)) {
                    return null;
                }
            }
        }
        userViewModel.setLogin(login);
        return restTemplate.postForEntity(backendServerUrl + "/login", userViewModel, UserViewModel.class).getBody();
    }

    @Override
    public UserViewModel updateUsersEmail(Long id, String email) {
        UserViewModel userViewModel = restTemplate.getForEntity(backendServerUrl + "/" + id, UserViewModel.class).getBody();
        if (userViewModel != null) {
            userViewModel.setEmail(email);
        }
        return restTemplate.postForEntity(backendServerUrl + "/email", userViewModel, UserViewModel.class).getBody();
    }

    @Override
    public UserViewModel updateUsersPassword(UserChangePasswordModel userChangePasswordModel) {
        UserViewModel userViewModel = restTemplate.getForEntity(backendServerUrl + "/" + userChangePasswordModel.getUserId(), UserViewModel.class).getBody();
        if (userViewModel != null && userChangePasswordModel.getOldPassword().equals(userViewModel.getPassword())) {
            userViewModel.setPassword(userChangePasswordModel.getNewPassword());
            return restTemplate.postForEntity(backendServerUrl + "/password", userViewModel, UserViewModel.class).getBody();
        } else {
            return null;
        }

    }

    @Override
    public UserViewModel getLoginUser(String login, String password) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setLogin(login);
        userViewModel.setPassword(password);
        return restTemplate.postForEntity(backendServerUrl + "/authorization", userViewModel, UserViewModel.class).getBody();
    }

    @Override
    public UserViewModel uploadImage(MultipartFile image, Long id) {
        return restTemplate.postForEntity(backendServerUrl + "/image/" + id,
                imageService.uploadImage(image), UserViewModel.class).getBody();
    }

    @Override
    public Resource getImage(String imageName) {
        return restTemplate.getForEntity(backendServerUrl + "/image/" + imageName, Resource.class).getBody();
    }

}
