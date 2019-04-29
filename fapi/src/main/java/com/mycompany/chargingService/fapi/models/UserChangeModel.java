package com.mycompany.chargingService.fapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserChangeModel {

    private Long userId;
    private String newLogin;
    private String oldPassword;
    private String newPassword;
    private String newEmail;

    public UserChangeModel() {
    }

    public UserChangeModel(Long userId, String newLogin, String oldPassword, String newPassword, String newEmail) {
        this.userId = userId;
        this.newLogin = newLogin;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newEmail = newEmail;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
