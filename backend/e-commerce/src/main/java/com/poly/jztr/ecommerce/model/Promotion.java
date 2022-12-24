package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @Column(name = "promo_code", unique = true)
    private String code;


    @Column(name = "reduction_amount")
    private Double amount;

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

    @Column(name =  "status", nullable = false)
    private Integer status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
    private List<Order> orders;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }

    public void setCode(String code) {
        this.code = code;
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



//    public void setType(Integer type) {
//        this.type = type;
//        this.updatedAt = Instant.now();
//    }
}
