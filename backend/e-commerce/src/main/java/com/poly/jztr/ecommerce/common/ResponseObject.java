package com.poly.jztr.ecommerce.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseObject {
    private Integer statusCode;
    private String message;
    private Object data;
}
