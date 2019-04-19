package com.mycompany.chargingService.fapi.validators;

import com.mycompany.chargingService.fapi.models.BillingAccount;

import java.util.List;

public interface BillingAccountValidator {

    String billingAccountValidation(BillingAccount billingAccount, List<BillingAccount>billingAccounts);
}
