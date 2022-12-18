package com.poly.jztr.ecommerce.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comments")
@Getter
public class Comment {

    public Comment(){
        this.createdAt = Instant.now();
    }
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", length = 100)
    private String firstName;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "content", length = 1000)
    private String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Comment(Long id) {
        this.id = id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setContent(String content){
        this.content = content;
        this.updatedAt = Instant.now();
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setParent(Comment parent) {
        this.parent = parent;
    }


    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }


    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

}