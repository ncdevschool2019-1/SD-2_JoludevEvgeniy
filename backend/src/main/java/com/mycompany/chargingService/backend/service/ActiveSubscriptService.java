package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;

import java.util.Optional;

public interface ActiveSubscriptService {

    ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript);
    void deleteActiveSubscript (Long id);
    Optional<ActiveSubscript> getActiveSubscriptById(Long id);

}
