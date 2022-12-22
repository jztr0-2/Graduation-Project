package com.poly.jztr.ecommerce.dto;


import com.poly.jztr.ecommerce.model.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NotBlank
    @Length(max = 255)
    private String name;
    private Integer status;
    private String description;
    @NotNull
    private Long categoryId;
    private List<ProductVariantDto> productVariantList;
}
