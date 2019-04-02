package com.mycompany.chargingService.backend.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String password;

    private String email;

    @ManyToOne()
    private Role role;

    private String imagePath;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private Set<BillingAccount> billingAccounts;

    public User(String login, String password, String email, Role role, String imagePath, Set<BillingAccount> billingAccounts) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imagePath = imagePath;
        this.billingAccounts = billingAccounts;
    }

    public User() {
    }

    public String getRole() {
        return role.getRoleName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Set<BillingAccount> getBillingAccounts() {
        return billingAccounts;
    }

    public void setBillingAccounts(Set<BillingAccount> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role) &&
                Objects.equals(imagePath, user.imagePath) &&
                Objects.equals(billingAccounts, user.billingAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role, imagePath, billingAccounts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", imagePath='" + imagePath + '\'' +
                ", billingAccounts=" + billingAccounts +
                '}';
    }
}
