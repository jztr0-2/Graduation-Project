package com.poly.jztr.ecommerce.expection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class ResponseError {
    private HttpStatus httpStatus;
    private String message;
    private Map<String, String> detailErrors;
}
