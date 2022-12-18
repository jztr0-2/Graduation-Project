package com.poly.jztr.ecommerce.serviceImpl;

import com.poly.jztr.ecommerce.dto.CommentDto;
import com.poly.jztr.ecommerce.model.Comment;
import com.poly.jztr.ecommerce.repository.CommentRepository;
import com.poly.jztr.ecommerce.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository repository;

    @Override
    public Page<Comment> findByProduct(Long productId, Pageable pageable) {
        return repository.findByProduct(productId, pageable);
    }

    @Override
    public Comment save(CommentDto dto) {
        Comment cmt = new Comment();
        BeanUtils.copyProperties(dto,cmt);
        if(dto.getParentId() != null){
            cmt.setParent(new Comment(dto.getParentId()));
        }
        return repository.save(cmt);
    }

    @Override
    public void update(CommentDto dto, Long id) {
        Comment cmt = repository.findById(id).get();
    }
}
