package com.mycompany.chargingService.backend.components;

import com.mycompany.chargingService.backend.entity.ActiveSubscript;
import com.mycompany.chargingService.backend.entity.BillingAccount;
import com.mycompany.chargingService.backend.service.ActiveSubscriptService;
import com.mycompany.chargingService.backend.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class WriteOff {

    private ActiveSubscriptService activeSubscriptService;
    private BillingAccountService billingAccountService;

    @Autowired
    public WriteOff(ActiveSubscriptService activeSubscriptService, BillingAccountService billingAccountService) {
        this.activeSubscriptService = activeSubscriptService;
        this.billingAccountService = billingAccountService;
    }

    @Scheduled(fixedDelay = 10000)
    public void checkActiveSubscripts() {
        this.activeSubscriptService.getAllActiveSubscripts().forEach(activeSubscript -> {
            if (Math.abs(this.activeSubscriptService.getTimesDifference(activeSubscript.getId())) >
                    activeSubscript.getSubscript().getPeriod()) {
                BillingAccount billingAccount = this.billingAccountService.getBillingAccountById(activeSubscript.getBillingAccountId());
                billingAccount.setBalance(billingAccount.getBalance() - activeSubscript.getSubscript().getPrice());
                if (billingAccount.isActive() && billingAccount.getBalance() < 0) {
                    billingAccount.setActive(false);
                }
                this.billingAccountService.saveBillingAccount(billingAccount);
                this.activeSubscriptService.setTimeNow(activeSubscript.getId());
            }
        });

    }
}
