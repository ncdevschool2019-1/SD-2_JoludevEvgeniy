package com.mycompany.chargingService.backend.controller;

import com.mycompany.chargingService.backend.entity.BillingAccount;
import com.mycompany.chargingService.backend.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {

    private BillingAccountService billingAccountService;

    @Autowired
    public BillingAccountController(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BillingAccount> getBillingAccountById(@PathVariable(name = "id") Long id) {
        Optional<BillingAccount> billingAccount = this.billingAccountService.getBillingAccountById(id);
        if(billingAccount.isPresent()){
            return ResponseEntity.ok(billingAccount.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public BillingAccount saveBillingAccount(@RequestBody BillingAccount billingAccount){
        return this.billingAccountService.saveBillingAccount(billingAccount);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<BillingAccount> getAllBillingAccounts(){
        return this.billingAccountService.getAllBillingAccounts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBillingAccount(@PathVariable(name = "id") Long id){
        this.billingAccountService.deleteBillingAccount(id);
    }


}
