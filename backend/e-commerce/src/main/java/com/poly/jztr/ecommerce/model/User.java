package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.jztr.ecommerce.common.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "deleted_at is null")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "status", nullable = false)
    @JsonIgnore
    private Integer status;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "created_at", nullable = false)
    @JsonIgnore
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonIgnore
    private Instant updatedAt;

    @Column(name = "deleted_at")
    @JsonIgnore
    private Instant deletedAt;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "reset_password_token")
    private String resetToken;

    @Column(name = "reset_sent_at")
    private Instant resetSentAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private List<Favorite> favorites;

    public User(Long userId) {
        this.id = userId;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deleteAt) {
        this.deletedAt = deleteAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPhone(String phone){
        if(phone.startsWith("+84")){
            this.phone = phone.replace("+84","0");
            this.updatedAt = Instant.now();
            return;
        }else if(phone.startsWith("84")){
            this.phone = "0" + phone.substring(2);
            this.updatedAt = Instant.now();
            return;
        }
        this.phone = phone;
        this.updatedAt = Instant.now();
    }

    public void setEmail(String email){
        this.email = email;
        this.updatedAt = Instant.now();
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
        this.updatedAt = Instant.now();
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.updatedAt = Instant.now();
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setPassword(String password){
        this.password = password;
        this.updatedAt = Instant.now();
    }

    public String getImage(){
        return image != null ? Constant.BASE_API_URL + "public/" + image.getTitle() : "";
     }

}
