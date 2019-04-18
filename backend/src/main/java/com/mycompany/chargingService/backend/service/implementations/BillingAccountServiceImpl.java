package com.mycompany.chargingService.backend.service.implementations;

import com.mycompany.chargingService.backend.entity.BillingAccount;
import com.mycompany.chargingService.backend.repository.BillingAccountRepository;
import com.mycompany.chargingService.backend.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillingAccountServiceImpl implements BillingAccountService {

    private BillingAccountRepository billingAccountRepository;

    @Autowired
    public BillingAccountServiceImpl(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

    @Override
    public BillingAccount saveBillingAccount(BillingAccount billingAccount) {
        return this.billingAccountRepository.save(billingAccount);
    }

    @Override
    public BillingAccount getBillingAccountById(Long id) {
        return this.billingAccountRepository.findById(id).isPresent() ?
                this.billingAccountRepository.findById(id).get() : null;
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
