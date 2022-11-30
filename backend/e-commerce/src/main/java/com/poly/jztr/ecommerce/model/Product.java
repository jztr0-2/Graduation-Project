package com.poly.jztr.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@NamedQueries({
    @NamedQuery(
        name = "Product.getProductsByCategoryId",
        query = "SELECT p FROM Product p WHERE p.category.id = ?1"
    )
})

@Entity
@Table(name = "products")
@Where(clause = "deleted_at is null")
@Getter
@Setter
public class Product {

    public Product() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    public List<ProductVariant> productVariants;

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }


    public void setName(String name) {
        this.name = name;
        this.updatedAt = Instant.now();
    }


    public void setStatus(Integer status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }


    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void setImage(Image image) {
        this.image = image;
        this.updatedAt = Instant.now();
    }

}