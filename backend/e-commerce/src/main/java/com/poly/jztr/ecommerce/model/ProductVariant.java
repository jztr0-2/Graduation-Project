package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.jztr.ecommerce.dto.ProductVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Table(name = "product_variant")
@Where(clause = "deleted_at is null")
@AllArgsConstructor
public class ProductVariant {

    public ProductVariant(){
        this.createdAt = Instant.now();
    }
    public ProductVariant(ProductVariantDto dto){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.setUnitPrice(dto.getUnitPrice());
        this.setQuantity(dto.getQuantity());
        this.setDescription(dto.getDescription());
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    @NotNull
    @Min(1)
    private Double unitPrice;

    @Column(name = "quantity")
    @NotNull
    @Min(0)
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "display_name", unique = true)
    private String displayName;

    public ProductVariant(Long productVariantId) {
        this.id = productVariantId;
    }

    public void setId(Long id) {
        this.id = id;
        this.updatedAt = Instant.now();
    }

    public void setProduct(Product product) {
        this.product = product;
        this.updatedAt = Instant.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        this.updatedAt = Instant.now();
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        this.updatedAt = Instant.now();
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        this.updatedAt = Instant.now();
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        this.updatedAt = Instant.now();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.updatedAt = Instant.now();
    }

    public void setImage(Image image) {
        this.image = image;
        this.updatedAt = Instant.now();
    }

    public void setDisplayName(String name){
        Map<String, String> map = getDescription();
        this.displayName = name + map.get("title");
    }

    public Map<String, String> getDescription() {
        if(description == null){
            return null;
        }
        description = description.substring(1, description.length() - 1);
        List<String> items = Arrays.asList(description.split(","));
        Map<String, String> map = new HashMap();
        items.stream().forEach(item->{
            int  position = item.indexOf(":");
            map.put(item.substring(0,position).trim().replace("\"",""),
                    item.substring(position+1).replace("\"",""));
        });
        return map;
    }

    /*
  TODO [JPA Buddy] create field to map the 'details' column
   Available actions: Define target Java type | Uncomment as is | Remove column mapping
  @Column(name = "details", columnDefinition = "JSON(1073741824) not null")
  private Object details;
*/
}