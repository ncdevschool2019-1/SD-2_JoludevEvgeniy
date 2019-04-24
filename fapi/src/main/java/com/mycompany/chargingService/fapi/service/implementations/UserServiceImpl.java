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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("customUserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${backend.server.url}api/users")
    private String backendServerUrl;
    private RestTemplate restTemplate = new RestTemplate();

    private ImageService imageService;
    private ConverterService converterService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ImageService imageService, ConverterService converterService) {
        this.imageService = imageService;
        this.converterService = converterService;
    }

    @Override
    public List<UserViewModel> getAllUsers() {
        User[] users = restTemplate.getForEntity(backendServerUrl, User[].class).getBody();
        if (users != null) {
            UserViewModel[] userViewModels = this.converterService.convertUsersArrayToUserViewModelsArray(users);
            return Arrays.asList(userViewModels);
        }
        return Collections.emptyList();
    }

    @Override
    public UserViewModel getUserById(Long id) {
        User user = restTemplate.getForEntity(backendServerUrl + "/" + id, User.class).getBody();
        if (user != null) {
            return this.converterService.convertUserToUserViewModel(user);
        }
        return null;
    }

    @Override
    public UserViewModel saveUser(UserViewModel userViewModel) {
        if (userViewModel != null) {
            userViewModel.setBillingAccounts(new ArrayList<>());
            userViewModel.setPassword(bCryptPasswordEncoder.encode(userViewModel.getPassword()));
            User user = restTemplate.postForEntity(backendServerUrl,
                    this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody();
            if (user != null) {
                return this.converterService.convertUserToUserViewModel(user);
            }
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }

    @Override
    public UserViewModel updateUsersLogin(UserViewModel userViewModel) {
        User user = this.restTemplate.getForEntity(this.backendServerUrl + "/" + userViewModel.getId(), User.class).getBody();
        if (user != null) {
            user.setLogin(userViewModel.getLogin());
            User updatableUser = this.restTemplate.postForEntity(this.backendServerUrl + "/login", user, User.class).getBody();
            if (updatableUser != null) {
                return this.converterService.convertUserToUserViewModel(updatableUser);
            }
        }
        return null;
    }

    @Override
    public UserViewModel updateUsersEmail(UserViewModel userViewModel) {
        User user = this.restTemplate.getForEntity(this.backendServerUrl + "/" + userViewModel.getId(), User.class).getBody();
        if (user != null) {
            user.setEmail(userViewModel.getEmail());
            User updatableUser = this.restTemplate.postForEntity(this.backendServerUrl + "/email", user, User.class).getBody();
            if (updatableUser != null) {
                return this.converterService.convertUserToUserViewModel(updatableUser);
            }
        }
        return null;
    }

    @Override
    public UserViewModel updateUsersPassword(UserChangePasswordModel userChangePasswordModel) {
        User user = this.restTemplate.getForEntity(this.backendServerUrl + "/" + userChangePasswordModel.getUserId(),
                User.class).getBody();
        if (user != null) {
            user.setPassword(userChangePasswordModel.getNewPassword());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User updatableUser = this.restTemplate.postForEntity(this.backendServerUrl + "/password",
                    user, User.class).getBody();
            if (updatableUser != null) {
                return this.converterService.convertUserToUserViewModel(updatableUser);
            }
        }
        return null;
    }

    @Override
    public UserViewModel getLoginUser(String login) {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setLogin(login);
        User user = restTemplate.postForEntity(backendServerUrl + "/authorization",
                this.converterService.convertUserViewModelToUser(userViewModel), User.class).getBody();
        if (user != null) {
            return this.converterService.convertUserToUserViewModel(user);
        }
        return null;
    }

    @Override
    public UserViewModel uploadImage(MultipartFile image, Long id) {
        User user = restTemplate.postForEntity(backendServerUrl + "/image/" + id,
                imageService.uploadImage(image), User.class).getBody();
        if(user != null) {
            return this.converterService.convertUserToUserViewModel(user);
        }
        return null;
    }

    @Override
    public Resource getImage(String imageName) {
        return restTemplate.getForEntity(backendServerUrl + "/image/" + imageName, Resource.class).getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserViewModel user = getLoginUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserViewModel user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

}
