package com.mycompany.chargingService.fapi.service.implementations;

import com.mycompany.chargingService.fapi.models.BillingAccount;
import com.mycompany.chargingService.fapi.service.BillingAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BillingAccountServiceImpl implements BillingAccountService {

    @Value("${backend.server.url}api/billing-accounts")
    private String backendServerUrl;
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<BillingAccount> getAllBillingAccounts() {
        BillingAccount[] billingAccounts = restTemplate.getForEntity(backendServerUrl, BillingAccount[].class).getBody();
        return billingAccounts == null ? Collections.emptyList() : Arrays.asList(billingAccounts);
    }

    @Override
    public BillingAccount getBillingAccountById(Long id) {
        return restTemplate.getForEntity(backendServerUrl + "/" + id, BillingAccount.class).getBody();
    }

    @Override
    public BillingAccount saveBillingAccount(BillingAccount billingAccount) {
        if(billingAccount.getId() == null){
            billingAccount.setActive(true);
            billingAccount.setActiveSubscripts(new ArrayList<>());
        }
        if(billingAccount.getBalance() < 0){
            billingAccount.setActive(false);
        }
        if(!billingAccount.isActive() && billingAccount.getBalance() > 0){
            billingAccount.setActive(true);
        }
        return restTemplate.postForEntity(backendServerUrl, billingAccount, BillingAccount.class).getBody();
    }

    @Override
    public void deleteBillingAccount(Long id) {
        restTemplate.delete(backendServerUrl + "/" + id);
    }
}
