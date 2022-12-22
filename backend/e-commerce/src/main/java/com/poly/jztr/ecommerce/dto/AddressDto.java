package com.poly.jztr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable {

    private Long id;
    private String province;
    private String district;
    private String commune;
    private String ward;
    private String appartmentNo;
    private String phone;

    public String getPhone(){
        if(phone.trim().startsWith("0")) {
            phone = "84" + phone.trim().substring(1);
        }
        return phone;
    }

}
