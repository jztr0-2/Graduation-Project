package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.service.ImageService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@NamedQueries({
        @NamedQuery(
            name = "Product.getProductsByCategoryId",
            query = "SELECT p FROM Product p WHERE p.category.id = ?1"
        ),
        @NamedQuery(
                name = "Product.countProducts",
                query = "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0"
        ),
        @NamedQuery(
                name = "Product.searchProducts",
                query = "SELECT p FROM Product p WHERE p.quantity > 0 and  p.name like ?1"
        ),
        @NamedQuery(
                name = "Product.getNewProducts",
                query = "SELECT p FROM Product p WHERE p.quantity > 0 ORDER BY p.createdAt DESC"
        )
})

@Entity
@Table(name = "products")
@Where(clause = "deleted_at is null OR deleted_at < updated_at")
@Getter
@Setter
public class Product {
    public Product() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Product(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @Fetch(FetchMode.JOIN)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    @Fetch(FetchMode.JOIN)
    private Brand brand;

    @Column(name = "unit_price")
    @NotNull
    @Min(1)
    private Long unitPrice;

    @Column(name = "quantity")
    @NotNull
    @Min(0)
    private Integer quantity;

    @Column(name = "image")
    private String img;

    @JsonInclude()
    @Transient
    private int totalSoldProduct;

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }


    public void setName(String name) {
        this.name = name;
        this.updatedAt = Instant.now();
    }


    public void setStatus(Boolean status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }


    public String getImage(){
        return this.img != null ? Constant.BASE_API_URL + "public/images/" + this.img : "";
    }
}