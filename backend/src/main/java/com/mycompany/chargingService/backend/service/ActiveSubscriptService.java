package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;

import java.util.Optional;

public interface ActiveSubscriptService {

    Iterable<ActiveSubscript> getAllActiveSubscripts();
    ActiveSubscript getActiveSubscriptById(Long id);
    ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript);
    void deleteActiveSubscript (Long id);
    ActiveSubscript setTimeNow(Long id);
    int getTimesDifference(Long id);


}
