package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAppartmentNo() {
        return appartmentNo;
    }

    public void setAppartmentNo(String appartmentNo) {
        this.appartmentNo = appartmentNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Address(){

    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "address")
    List<Order> order;
}