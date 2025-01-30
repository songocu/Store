package com.example.stroretool.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// I do not understand why my Ide does not let me use Record from Java 17. He will not see the getters and setters
//public record Product(Long id, String name, BigDecimal price, String category, LocalDateTime addDate, LocalDateTime modifiedDate) {}


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private Long id;

    private String name;
    private String category;
    private Integer number;
    private BigDecimal price;
    private LocalDateTime addDate;
    private LocalDateTime modifiedDate;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}