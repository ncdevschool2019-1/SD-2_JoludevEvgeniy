package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import com.mycompany.chargingService.backend.repository.ActiveSubscriptRepository;
import com.mycompany.chargingService.backend.service.ActiveSubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveSubscriptServiceImplement implements ActiveSubscriptService {

    private ActiveSubscriptRepository repository;

    @Autowired
    public ActiveSubscriptServiceImplement(ActiveSubscriptRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActiveSubscript saveActiveSubscript(ActiveSubscript activeSubscript) {
        return this.repository.save(activeSubscript);
    }

    @Override
    public void deleteActiveSubscript(Long id) {
        this.repository.deleteById(id);
    }
}
