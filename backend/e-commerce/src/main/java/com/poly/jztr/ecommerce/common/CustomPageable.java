package com.poly.jztr.ecommerce.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageable {
    public static Pageable getDefaultPage(String sortBy, int order){
        Sort sort = null;
        if(order == 1){
            sort = Sort.by(Sort.Direction.ASC,sortBy);
        }else{
            sort = Sort.by(Sort.Direction.DESC,sortBy);
        }
        return PageRequest.of(0,10,sort);
    }
}
