package com.mycompany.chargingService.fapi.service;

import com.mycompany.chargingService.fapi.models.BillingAccount;

import java.util.List;

public interface BillingAccountService {

    List<BillingAccount> getAllBillingAccounts();

    BillingAccount getBillingAccountById(Long id);

    BillingAccount saveBillingAccount(BillingAccount billingAccount);

    void deleteBillingAccount(Long id);
}
