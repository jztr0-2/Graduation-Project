package com.poly.jztr.ecommerce.dto;

import com.poly.jztr.ecommerce.common.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {

    @NotNull
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Double percent;

    @NotNull
    private Double amount;

    @NotNull
    private Double maxAmount;

    @NotNull
    private Instant endDate;

    @NotNull
    private Instant startDate;

    @NotNull
    private Boolean status;
    private Long type;

    public void setStartDate(String date){
        this.startDate = DateHelper.toDate(date, "dd-MM-yyyy");
    }

    public void setEndDate(String date){
        this.endDate = DateHelper.toDate(date, "dd-MM-yyyy");
    }

}
