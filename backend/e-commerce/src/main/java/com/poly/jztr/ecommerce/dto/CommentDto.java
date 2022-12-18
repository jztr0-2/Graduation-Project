package com.poly.jztr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private String email;
    private String phone;
    private String name;
    private Long userId;
    private Long productId;
    private Long parentId;
    private String content;
}
