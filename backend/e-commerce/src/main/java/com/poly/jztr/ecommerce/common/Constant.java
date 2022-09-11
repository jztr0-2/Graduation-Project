package com.poly.jztr.ecommerce.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    @Value("${jwt.username}")
    public static String USERNAME;
}
