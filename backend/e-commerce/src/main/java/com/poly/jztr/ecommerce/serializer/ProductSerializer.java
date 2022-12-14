package com.poly.jztr.ecommerce.serializer;

import com.poly.jztr.ecommerce.model.Category;
import com.poly.jztr.ecommerce.model.Image;
import com.poly.jztr.ecommerce.model.ProductVariant;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class ProductSerializer {
    private Long id;
    private String name;
    private Integer status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private String description;
    private String image;
    private Category category;
    public List<ProductVariant> productVariants;
    public List<String> imageList;
}
