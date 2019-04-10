package com.mycompany.chargingService.fapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveSubscript {

    private Long id;
    private Long billingAccountId;
    private Subscript subscript;
    private String lastWriteOff;

    public ActiveSubscript() {
    }

    public ActiveSubscript(Long id, Long billingAccountId, Subscript subscript, String lastWriteOff) {
        this.id = id;
        this.billingAccountId = billingAccountId;
        this.subscript = subscript;
        this.lastWriteOff = lastWriteOff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(Long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    public Subscript getSubscript() {
        return subscript;
    }

    public void setSubscript(Subscript subscript) {
        this.subscript = subscript;
    }

    public String getLastWriteOff() {
        return lastWriteOff;
    }

    public void setLastWriteOff(String lastWriteOff) {
        this.lastWriteOff = lastWriteOff;
    }
}
