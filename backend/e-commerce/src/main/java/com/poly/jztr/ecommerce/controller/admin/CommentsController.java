package com.poly.jztr.ecommerce.controller.admin;

import com.poly.jztr.ecommerce.common.Constant;
import com.poly.jztr.ecommerce.common.CustomPageable;
import com.poly.jztr.ecommerce.common.ResponseObject;
import com.poly.jztr.ecommerce.model.Comment;
import com.poly.jztr.ecommerce.serializer.PageableSerializer;
import com.poly.jztr.ecommerce.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin/comment")
@RequestMapping("api/v1/admin/comments")
public class CommentsController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseObject> getListRecent(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer perPage){
        Pageable pageable = CustomPageable.getPage(page,perPage, "created_at",Constant.SORT_DESC);
        Page<Comment> commentPage = commentService.findParentComment(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Constant.RESPONSE_STATUS_SUCCESS,
                        "Get list comment successfully",
                        new PageableSerializer(commentPage)));
    }
}
