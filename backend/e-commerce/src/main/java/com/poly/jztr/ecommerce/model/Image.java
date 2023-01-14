package com.poly.jztr.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
@Getter
@Setter
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

    @Column(name = "created_at")
    private Instant createdAt;
}