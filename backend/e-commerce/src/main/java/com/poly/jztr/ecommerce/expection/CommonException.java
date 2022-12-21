package com.poly.jztr.ecommerce.expection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommonException extends Exception {
    String name;
    String message;
}
