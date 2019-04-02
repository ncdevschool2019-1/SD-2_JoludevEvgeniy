package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;

public interface ActiveSubscriptService {

    ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript);
    void deleteActiveSubscript (Long id);

}
