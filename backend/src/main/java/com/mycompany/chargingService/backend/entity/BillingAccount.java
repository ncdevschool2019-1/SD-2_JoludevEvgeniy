package com.mycompany.chargingService.backend.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "billing_accounts")
public class BillingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int balance;

    private boolean active;

    private long userId;
    @OneToMany(mappedBy = "billingAccountId", cascade = CascadeType.ALL)
    private List<ActiveSubscript> activeSubscripts;

    public BillingAccount(String name, int balance, boolean active, long userId, List<ActiveSubscript> activeSubscripts) {
        this.name = name;
        this.balance = balance;
        this.active = active;
        this.userId = userId;
        this.activeSubscripts = activeSubscripts;
    }

    public BillingAccount() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ActiveSubscript> getActiveSubscripts() {
        return activeSubscripts;
    }

    public void setActiveSubscripts(List<ActiveSubscript> activeSubscripts) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingAccount that = (BillingAccount) o;
        return id == that.id &&
                balance == that.balance &&
                active == that.active &&
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(activeSubscripts, that.activeSubscripts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance, active, userId, activeSubscripts);
    }

    @Override
    public String toString() {
        return "BillingAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                ", userId=" + userId +
                ", activeSubscripts=" + activeSubscripts +
                '}';
    }
}
