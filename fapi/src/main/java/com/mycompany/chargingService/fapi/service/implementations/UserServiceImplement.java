package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    @Value("${backend.server.url}api/users")
    private String backendServerUrl;
    private RestTemplate restTemplate = new RestTemplate();

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
        return restTemplate.postForEntity(backendServerUrl, user, User.class).getBody();
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }
}
