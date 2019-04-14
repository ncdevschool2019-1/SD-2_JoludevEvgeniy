package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.models.UserChangePasswordModel;
import com.mycompany.chargingService.fapi.service.ConverterService;
import com.mycompany.chargingService.fapi.service.ImageService;
import com.mycompany.chargingService.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    @Value("${backend.server.url}api/users")
    private String backendServerUrl;
    private RestTemplate restTemplate = new RestTemplate();

    private ImageService imageService;
    private ConverterService converterService;

    @Autowired
    public UserServiceImplement(ImageService imageService, ConverterService converterService) {
        this.imageService = imageService;
        this.converterService = converterService;
    }

    @Override
    public List<UserViewModel> getAllUsers() {
        UserViewModel[] userViewModels = this.converterService.convertUsersArrayToUserViewModelsArray(
                restTemplate.getForEntity(backendServerUrl, User[].class).getBody());
        return userViewModels == null ? Collections.emptyList() : Arrays.asList(userViewModels);
    }

    @Override
    public UserViewModel getUserById(Long id) {
        return this.converterService.convertUserToUserViewModel(
                restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody());
    }

    @Override
    public UserViewModel saveUser(UserViewModel userViewModel) {
        userViewModel.setBillingAccounts(new ArrayList<>());
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(backendServerUrl,
                this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody());
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }

    @Override
    public UserViewModel updateUsersLogin(Long id, String login) {
        UserViewModel userViewModel = this.converterService.convertUserToUserViewModel(
                restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody());
        if (userViewModel != null) {
            userViewModel.setLogin(login);
        }
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(
                backendServerUrl + "/login",
                this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody());
    }

    @Override
    public UserViewModel updateUsersEmail(Long id, String email) {
        UserViewModel userViewModel = this.converterService.convertUserToUserViewModel(
                restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody());
        if (userViewModel != null) {
            userViewModel.setEmail(email);
        }
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(backendServerUrl + "/email",
                this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody());
    }

    @Override
    public UserViewModel updateUsersPassword(UserChangePasswordModel userChangePasswordModel) {
        UserViewModel userViewModel = this.converterService.convertUserToUserViewModel(
                restTemplate.getForEntity(backendServerUrl + "/" + userChangePasswordModel.getUserId(), User.class).getBody());

        userViewModel.setPassword(userChangePasswordModel.getNewPassword());
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(backendServerUrl + "/password",
                this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody());

    }

    @Override
    public UserViewModel getLoginUser(String login, String password) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setLogin(login);
        userViewModel.setPassword(password);
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(
                backendServerUrl + "/authorization", this.converterService.convertUserViewModelToUser(userViewModel),
                User.class).getBody());
    }

    @Override
    public UserViewModel uploadImage(MultipartFile image, Long id) {
        return this.converterService.convertUserToUserViewModel(restTemplate.postForEntity(backendServerUrl + "/image/" + id,
                imageService.uploadImage(image), User.class).getBody());
    }

    @Override
    public Resource getImage(String imageName) {
        return restTemplate.getForEntity(backendServerUrl + "/image/" + imageName, Resource.class).getBody();
    }

}
