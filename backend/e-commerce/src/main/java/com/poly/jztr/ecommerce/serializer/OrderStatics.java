package com.poly.jztr.ecommerce.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatics {
    private Long customerCount;
    private Long productSold;
    private Long orderSold;
    private Double earning;
    private List<Object []> totalRevenue;
    private List<ProductStatic> bestSelling;
    private List<ProductStatic> lowestSelling;
}
