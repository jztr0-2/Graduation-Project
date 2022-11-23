package com.poly.jztr.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "promotion")
@Getter
public class Promotion {

    public Promotion(){
        this.createdAt = Instant.now();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "promo_code")
    private String code;

    @Column(name = "reduction_percent")
    private Double percent;

    @Column(name = "reduction_amount")
    private Double amount;

    @Column(name = "max_reduction_amount")
    private Double maxAmount;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "type")

    private Long type;

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }

    public void setCode(String code) {
        this.code = code;
        this.updatedAt = Instant.now();
    }

    public void setPercent(Double percent) {
        this.percent = percent;
        this.updatedAt = Instant.now();
    }

    public void setAmount(Double amount) {
        this.amount = amount;
        this.updatedAt = Instant.now();
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
        this.updatedAt = Instant.now();
    }

    public void setStartDate(Instant expire) {
        this.startDate = expire;
        this.updatedAt = Instant.now();
    }

    public void setEndDate(Instant expire) {
        this.endDate = expire;
        this.updatedAt = Instant.now();
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        this.updatedAt = Instant.now();
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        this.updatedAt = Instant.now();
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        this.updatedAt = Instant.now();
    }



    public void setType(Long type) {
        this.type = type;
        this.updatedAt = Instant.now();
    }
}
