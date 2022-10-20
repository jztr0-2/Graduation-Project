package com.poly.jztr.ecommerce.expection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DuplicateEntryException extends Exception{
    private String field;
    private String message;
}
