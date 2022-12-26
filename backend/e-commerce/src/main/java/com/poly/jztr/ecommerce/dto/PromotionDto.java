package com.poly.jztr.ecommerce.dto;

import com.poly.jztr.ecommerce.common.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {

    private Long id;

    @NotNull
    @Pattern(message = "Promotion code is 6 character only", regexp = "^[a-zA-Z0-9]{6}$")
    private String code;


    @NotNull
    @Min(message = "Min amount is 1", value = 1)
    private Double amount;

    @NotNull
    private String endDate;

    @NotNull
    private String startDate;

    @Min(message = "Status is 1 or 2", value = 1)
    @Max(message = "Status is 1 or 2", value = 2)
    private Integer status;
}
