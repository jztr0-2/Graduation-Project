package com.poly.jztr.ecommerce.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.poly.jztr.ecommerce.model.Category;

import net.bytebuddy.utility.dispatcher.JavaDispatcher.Instance;

public class CategoryDto {

    @NotNull
    private Long id;

    @NotNull
    private Category parent;

    @NotNull
    private String name;

    @NotNull
    
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    @NotNull
    private Instant deleteAt;
}
