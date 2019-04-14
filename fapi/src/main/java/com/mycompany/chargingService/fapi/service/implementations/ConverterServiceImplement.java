package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.Role;
import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.service.ConverterService;
import org.springframework.stereotype.Service;

@Service
public class ConverterServiceImplement implements ConverterService {
    @Override
    public UserViewModel convertUserToUserViewModel(User user) {
        if (user != null) {
            UserViewModel userViewModel = new UserViewModel();
            userViewModel.setId(user.getId());
            userViewModel.setLogin(user.getLogin());
            userViewModel.setPassword(user.getPassword());
            userViewModel.setEmail(user.getEmail());
            userViewModel.setImagePath(user.getImagePath());
            userViewModel.setBillingAccounts(user.getBillingAccounts());
            userViewModel.setRole(user.getRole().getRole());
            return userViewModel;
        }
        return null;
    }

    @Override
    public UserViewModel[] convertUsersArrayToUserViewModelsArray(User[] users) {
        if (users != null) {
            UserViewModel[] userViewModels = new UserViewModel[users.length];
            for (int i = 0; i < users.length; i++) {
                userViewModels[i] = convertUserToUserViewModel(users[i]);
            }
            return userViewModels;
        }
        return null;
    }

    @Override
    public User convertUserViewModelToUser(UserViewModel userViewModel) {
        if (userViewModel != null) {
            User user = new User();
            user.setId(userViewModel.getId());
            user.setLogin(userViewModel.getLogin());
            user.setPassword(userViewModel.getPassword());
            user.setEmail(userViewModel.getEmail());
            user.setImagePath(userViewModel.getImagePath());
            user.setBillingAccounts(userViewModel.getBillingAccounts());
            Role role = new Role();
            if (userViewModel.getRole() != null) {
                switch (userViewModel.getRole()) {
                    case "User":
                        role.setId(1L);
                        break;
                    case "Admin":
                        role.setId(2L);
                        break;
                }
            } else {
                role.setId(1L);
                role.setRole("User");
            }
            user.setRole(role);
            return user;
        }
        return null;
    }
}
