package com.mycompany.chargingService.backend.service;

import com.mycompany.chargingService.backend.entity.BillingAccount;

import java.util.Optional;

public interface BillingAccountService {

    BillingAccount saveBillingAccount(BillingAccount billingAccount);
    Optional<BillingAccount> getBillingAccountById(Long id);
    Iterable<BillingAccount> getAllBillingAccounts();
    void deleteBillingAccount(Long id);

}
