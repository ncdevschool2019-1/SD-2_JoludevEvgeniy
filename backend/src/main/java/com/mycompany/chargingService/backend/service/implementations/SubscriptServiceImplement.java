package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.Subscript;
import com.mycompany.chargingService.backend.repository.SubscriptRepository;
import com.mycompany.chargingService.backend.service.SubscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptServiceImplement implements SubscriptService {

    private SubscriptRepository repository;

    @Autowired
    public SubscriptServiceImplement(SubscriptRepository repository) {
        this.repository = repository;
    }

    @Override
    public Subscript saveSubscript(Subscript subscript) {
        return this.repository.save(subscript);
    }

    @Override
    public Optional<Subscript> getSubscriptById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Iterable<Subscript> getAllSubscripts() {
        return this.repository.findAll();
    }

    @Override
    public void deleteSubscript(Long id) {
        this.repository.deleteById(id);
    }

}
