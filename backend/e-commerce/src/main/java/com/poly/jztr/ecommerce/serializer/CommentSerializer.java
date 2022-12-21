package com.poly.jztr.ecommerce.serializer;

import com.poly.jztr.ecommerce.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CommentSerializer {
    Long id;
    Long userId;
    String userImg;
    String content;
    Long parentId;
    Instant cratedAt;
    Instant updatedAt;
    Long productId;
    String productImg;

    String username;

    public CommentSerializer(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.cratedAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.productId = comment.getProduct().getId();
        this.productImg = comment.getProduct().getImage();
        this.userId = comment.getUser().getId();
        this.userImg = comment.getUser().getImage();
        this.username = comment.getUser().getFirstName() + comment.getUser().getLastName();
    }
}
