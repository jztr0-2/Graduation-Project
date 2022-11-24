package com.poly.jztr.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "commune", length = 50)
    private String commune;

    @Column(name = "ward", length = 50)
    private String ward;

    @Column(name = "appartment_no", length = 50)
    private String appartmentNo;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    public Address(Long addressId) {
        this.id =addressId;
    }

    public Address(){
    }
}