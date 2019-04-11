package com.mycompany.chargingService.fapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserViewModel {

    private Long id;
    private String login;
    private String password;
    private String email;
    private String imagePath;
    private String role;
    private List<BillingAccount> billingAccounts;

    public UserViewModel() {
    }

    public UserViewModel(Long id, String login, String password, String email, String imagePath, String role, List<BillingAccount> billingAccounts) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.imagePath = imagePath;
        this.role = role;
        this.billingAccounts = billingAccounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<BillingAccount> getBillingAccounts() {
        return billingAccounts;
    }

    public void setBillingAccounts(List<BillingAccount> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }
}
