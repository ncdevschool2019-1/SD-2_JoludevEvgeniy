package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.BillingAccount;
import com.mycompany.chargingService.fapi.service.BillingAccountService;
import com.mycompany.chargingService.fapi.validators.BillingAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {

    private BillingAccountService billingAccountService;

    private BillingAccountValidator billingAccountValidator;

    @Autowired
    public BillingAccountController(BillingAccountService billingAccountService, BillingAccountValidator billingAccountValidator) {
        this.billingAccountService = billingAccountService;
        this.billingAccountValidator = billingAccountValidator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BillingAccount>> getAllBillingAccounts() {
        return ResponseEntity.ok(this.billingAccountService.getAllBillingAccounts());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BillingAccount> getBillingAccountById(@PathVariable(name = "id") Long id) {
        BillingAccount billingAccount = this.billingAccountService.getBillingAccountById(id);
        if (billingAccount != null) {
            return ResponseEntity.ok(billingAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveBillingAccount(@RequestBody BillingAccount billingAccount) {
        String answer = this.billingAccountValidator.billingAccountValidation(billingAccount, this.billingAccountService.getAllBillingAccounts());
        if(answer.equals("Ok")){
            BillingAccount savedBillingAccount = this.billingAccountService.saveBillingAccount(billingAccount);
            if (savedBillingAccount != null) {
                return ResponseEntity.ok(savedBillingAccount);
            }
        }
        return ResponseEntity.badRequest().body(answer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBillingAccount(@PathVariable(name = "id") Long id) {
        this.billingAccountService.deleteBillingAccount(id);
    }
}
