package com.mycompany.chargingService.fapi.validators.impl;

import com.mycompany.chargingService.fapi.models.BillingAccount;
import com.mycompany.chargingService.fapi.validators.BillingAccountValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingAccountValidatorImpl implements BillingAccountValidator {
    @Override
    public String billingAccountValidation(BillingAccount billingAccount, List<BillingAccount> billingAccounts) {
        if (billingAccount.getName().length() < 3 || billingAccount.getName().length() > 30) {
            return "Lengths of your billing account name should be between 3 and 30 symbols";
        }
        for (BillingAccount value : billingAccounts) {
            if (billingAccount.getUserId().equals(value.getUserId()) && value.getName().equals(billingAccount.getName())) {
                return "Billing account with the same name you already have";
            }
        }
        return "Ok";
    }
}
