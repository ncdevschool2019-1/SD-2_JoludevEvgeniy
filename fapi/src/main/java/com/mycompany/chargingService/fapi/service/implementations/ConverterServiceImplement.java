package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.User;
import com.mycompany.chargingService.fapi.models.UserViewModel;
import com.mycompany.chargingService.fapi.service.ConverterService;
import org.springframework.stereotype.Service;

@Service
public class ConverterServiceImplement implements ConverterService {
    @Override
    public UserViewModel convertUserToUserViewModel(User user) {
        return null;
    }
}
