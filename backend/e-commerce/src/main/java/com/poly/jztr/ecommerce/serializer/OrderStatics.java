package com.poly.jztr.ecommerce.serializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatics {
    private Long customer;
    private Long productSold;
    private Long orderSold;
    private Double earning;
    private Double [] totalRevenue;
    private ProductStatic [] bestSelling;
    private ProductStatic [] lowestSelling;
}
