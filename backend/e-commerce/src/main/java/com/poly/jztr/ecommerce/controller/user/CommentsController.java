package com.poly.jztr.ecommerce.controller.user;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.dto.CommentDto;
import com.poly.jztr.ecommerce.model.Comment;
import com.poly.jztr.ecommerce.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("user/comments")
@RequestMapping("api/v1/user/comments")
public class CommentsController {
    @Autowired
    CommentService service;

    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody CommentDto dto){
        Comment cmt = service.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                Constant.RESPONSE_STATUS_SUCCESS,
                "Create comment successfully", cmt));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody CommentDto dto, @PathVariable Long id){
        service.update(dto, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                Constant.RESPONSE_STATUS_SUCCESS,
                "Create comment successfully", ""));
    }
}
