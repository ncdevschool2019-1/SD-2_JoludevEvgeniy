package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.BillingAccount;
import com.mycompany.chargingService.backend.repository.BillingAccountRepository;
import com.mycompany.chargingService.backend.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingAccountServiceImplement implements BillingAccountService {

    private BillingAccountRepository repository;

    @Autowired
    public BillingAccountServiceImplement(BillingAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public BillingAccount saveBillingAccount(BillingAccount billingAccount) {
        return this.repository.save(billingAccount);
    }

    @Override
    public Optional<BillingAccount> getBillingAccountById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Iterable<BillingAccount> getAllBillingAccounts() {
        return this.repository.findAll();
    }

    @Override
    public void deleteBillingAccount(Long id) {
        this.repository.deleteById(id);
    }
}
