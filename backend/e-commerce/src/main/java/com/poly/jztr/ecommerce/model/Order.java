package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "total")
    private Double total;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public Order(){
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.updatedAt = Instant.now();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.updatedAt = Instant.now();
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.updatedAt = Instant.now();
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.updatedAt = Instant.now();
        this.description = description;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}