package com.poly.jztr.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@NamedQueries({
        @NamedQuery(
                name = "OrderItem.totalSoldProductsByProductId",
                query = "SELECT sum(o.quantity) FROM OrderItem o WHERE o.product.id = ?1"
        ),
        @NamedQuery(
                name = "OrderItem.getDealOfDay",
                query = "SELECT o.product FROM OrderItem o GROUP BY o.product ORDER BY count(o.product), o.updatedAt DESC "
        ),
        @NamedQuery(
                name = "OrderItem.getBestSellerProducts",
                query = "SELECT o.product FROM OrderItem o GROUP BY o.product ORDER BY COUNT (o.product) DESC"
        )

})
@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.updatedAt = Instant.now();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
}
