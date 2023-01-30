package com.poly.jztr.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import java.time.Instant;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "Category.findPageCategory",
                query = "SELECT c FROM Category c WHERE c.products.size > 1"
        ),
        @NamedQuery(
                name = "Category.getSellingLimit",
                query = "SELECT o.product.category FROM OrderItem o group by o.product.category ORDER BY COUNT(o.product.category) DESC"
        )
})

@Entity
@Table(name = "categories")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100)
    @NotBlank
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

    // This field not column in db
    @JsonInclude()
    @Transient
    private int quantityProduct;
   public Category(){
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
   }

    public Category(Long id) {
       this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public void setQuantityProduct(int quantityProduct) {
       this.quantityProduct = quantityProduct;
    }
}