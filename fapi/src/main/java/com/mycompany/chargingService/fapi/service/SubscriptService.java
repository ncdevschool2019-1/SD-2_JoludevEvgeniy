package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.Subscript;

import java.util.List;

public interface SubscriptService {

    List<Subscript> getAllSubscripts();

    Subscript getSubscriptById(Long id);

    Subscript saveSubscript(Subscript subscript);

    void deleteSubscript(Long id);
}
