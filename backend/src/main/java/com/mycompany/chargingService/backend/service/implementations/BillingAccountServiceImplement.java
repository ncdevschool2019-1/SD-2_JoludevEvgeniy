package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.BillingAccount;
import com.mycompany.chargingService.backend.repository.BillingAccountRepository;
import com.mycompany.chargingService.backend.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingAccountServiceImplement implements BillingAccountService {

    private BillingAccountRepository billingAccountRepository;

    @Autowired
    public BillingAccountServiceImplement(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

    @Override
    public BillingAccount saveBillingAccount(BillingAccount billingAccount) {
        billingAccount.setActive(true);
        return this.billingAccountRepository.save(billingAccount);
    }

    @Override
    public Optional<BillingAccount> getBillingAccountById(Long id) {
        return this.billingAccountRepository.findById(id);
    }

    @Override
    public Iterable<BillingAccount> getAllBillingAccounts() {
        return this.billingAccountRepository.findAll();
    }

    @Override
    public void deleteBillingAccount(Long id) {
        this.billingAccountRepository.deleteById(id);
    }
}
