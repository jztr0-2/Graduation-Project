package com.poly.jztr.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    private Boolean status;
    private String description;
    @NotNull
    private Long categoryId;
    private Long brandId;
    private Long id;
    private Long unitPrice;
    private Integer quantity;
}
