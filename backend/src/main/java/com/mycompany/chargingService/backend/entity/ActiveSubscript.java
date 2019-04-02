package com.mycompany.chargingService.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "active_subscripts")
public class ActiveSubscript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long billingAccountId;
    @ManyToOne()
    private Subscript subscript;
    @Column(name = "last_write_off", insertable=false)
    private Timestamp lastWriteOff;

    public ActiveSubscript(long billingAccountId, Subscript subscript, Timestamp lastWriteOff) {
        this.billingAccountId = billingAccountId;
        this.subscript = subscript;
        this.lastWriteOff = lastWriteOff;
    }

    public ActiveSubscript() {
    }

    public Subscript getSubscript() {
        return subscript;
    }

    public void setSubscript(Subscript subscript) {
        this.subscript = subscript;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getLastWriteOff() {
        return lastWriteOff;
    }

    public long getBillingAccountId() {
        return billingAccountId;
    }

    public void setBillingAccountId(long billingAccountId) {
        this.billingAccountId = billingAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveSubscript that = (ActiveSubscript) o;
        return id == that.id &&
                billingAccountId == that.billingAccountId &&
                Objects.equals(subscript, that.subscript) &&
                Objects.equals(lastWriteOff, that.lastWriteOff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billingAccountId, subscript, lastWriteOff);
    }

    @Override
    public String toString() {
        return "ActiveSubscript{" +
                "id=" + id +
                ", billingAccountId=" + billingAccountId +
                ", subscript=" + subscript +
                ", lastWriteOff=" + lastWriteOff +
                '}';
    }
}
