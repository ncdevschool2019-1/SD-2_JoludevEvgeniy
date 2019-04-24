package com.mycompany.chargingService.fapi.controller;

import com.mycompany.chargingService.fapi.models.ActiveSubscript;
import com.mycompany.chargingService.fapi.models.BillingAccount;
import com.mycompany.chargingService.fapi.service.ActiveSubscriptService;
import com.mycompany.chargingService.fapi.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/active-subscripts")
public class ActiveSubscriptController {

    private ActiveSubscriptService activeSubscriptService;

    @Autowired
    private BillingAccountService billingAccountService;

    @Autowired
    public ActiveSubscriptController(ActiveSubscriptService activeSubscriptService) {
        this.activeSubscriptService = activeSubscriptService;
    }

    @PreAuthorize("hasRole('User')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ActiveSubscript>> getAllActiveSubscripts(){
        return ResponseEntity.ok(this.activeSubscriptService.getAllActiveSubscripts());
    }

    @PreAuthorize("hasRole('User')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ActiveSubscript> getActiveSubscriptById(@PathVariable(name = "id") Long id){
        ActiveSubscript activeSubscript = this.activeSubscriptService.getActiveSubscriptById(id);
        if(activeSubscript != null){
            return ResponseEntity.ok(activeSubscript);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('User')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ActiveSubscript> saveActiveSubscript(@RequestBody ActiveSubscript activeSubscript){
        ActiveSubscript savedActiveSubscript = this.activeSubscriptService.saveActiveSubscript(activeSubscript);
        if(savedActiveSubscript != null){
            BillingAccount billingAccount = this.billingAccountService.getBillingAccountById(activeSubscript.getBillingAccountId());
            billingAccount.setBalance(billingAccount.getBalance() - activeSubscript.getSubscript().getPrice());
            this.billingAccountService.saveBillingAccount(billingAccount);
            return ResponseEntity.ok(savedActiveSubscript);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('User')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteActiveSubscript(@PathVariable(name = "id") Long id){
        this.activeSubscriptService.deleteActiveSubscript(id);
    }
}
