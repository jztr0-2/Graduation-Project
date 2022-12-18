package com.poly.jztr.ecommerce.repository;

import com.poly.jztr.ecommerce.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByProduct(Long productId, Pageable pageable);

    Page<Comment> findByParent(Comment comment, Pageable pageable);
}
