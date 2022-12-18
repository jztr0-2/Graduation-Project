package com.poly.jztr.ecommerce.service;

import com.poly.jztr.ecommerce.dto.CommentDto;
import com.poly.jztr.ecommerce.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<Comment> findByProduct(Long productId, Pageable pageable);

    Comment save(CommentDto dto);

    Comment save(Comment dto);

    void update(CommentDto dto, Long id);
}
