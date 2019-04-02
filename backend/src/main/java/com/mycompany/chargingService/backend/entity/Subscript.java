package com.mycompany.chargingService.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subscripts")
public class Subscript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int price;
    private int period;
    private String imagePath;

    public Subscript(long id, String name, String description, int price, int period, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.period = period;
        this.imagePath = imagePath;
    }

    public Subscript() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscript subscript = (Subscript) o;
        return id == subscript.id &&
                price == subscript.price &&
                period == subscript.period &&
                name.equals(subscript.name) &&
                description.equals(subscript.description) &&
                imagePath.equals(subscript.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, period, imagePath);
    }

    @Override
    public String toString() {
        return "Subscript{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", period=" + period +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
