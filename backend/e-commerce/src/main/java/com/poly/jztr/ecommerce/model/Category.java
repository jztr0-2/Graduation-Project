package com.poly.jztr.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        )
})

@Entity
@Table(name = "categories")
@Where(clause = "delete_at is null OR updated_at > delete_at")
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

    @Column(name = "delete_at")
    private Instant deleteAt;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

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
}