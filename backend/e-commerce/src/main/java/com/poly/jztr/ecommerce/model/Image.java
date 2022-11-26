package com.poly.jztr.ecommerce.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
@Getter
public class Image {
    public Image(){
        this.createdAt = Instant.now();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "type")
    private Integer type;

    @Column(name = "url", length = 200)
    private String url;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = Instant.now();
    }

    public void setType(Integer type) {
        this.type = type;
        this.updatedAt = Instant.now();
    }

    public void setUrl(String url) {
        this.url = url;
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
}