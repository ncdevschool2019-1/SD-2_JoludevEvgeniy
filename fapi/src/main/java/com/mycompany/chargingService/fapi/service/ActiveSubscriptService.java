package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.ActiveSubscript;

import java.util.List;

public interface ActiveSubscriptService {

    List<ActiveSubscript> getAllActiveSubscript();

    ActiveSubscript getActiveSubscriptById(Long id);

    ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript);

    void deleteActiveSubscript(Long id);
}
