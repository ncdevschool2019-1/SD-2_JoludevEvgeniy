package com.mycompany.chargingService.fapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingAccount {

    private Long id;
    private String name;
    private int balance;
    private Long userId;
    private boolean active;
    private List<ActiveSubscript> activeSubscripts;

    public BillingAccount() {
    }

    public BillingAccount(Long id, String name, int balance, Long userId, boolean active, List<ActiveSubscript> activeSubscripts) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.userId = userId;
        this.active = active;
        this.activeSubscripts = activeSubscripts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ActiveSubscript> getActiveSubscripts() {
        return activeSubscripts;
    }

    public void setActiveSubscripts(List<ActiveSubscript> activeSubscripts) {
        this.activeSubscripts = activeSubscripts;
    }
}
