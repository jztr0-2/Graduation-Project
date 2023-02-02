package com.poly.jztr.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    private String phone;
    private String password;
    private String passwordConfirmation;
    private String code;
}
