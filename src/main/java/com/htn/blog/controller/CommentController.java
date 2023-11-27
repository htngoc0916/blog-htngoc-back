package com.htn.blog.controller;


import com.htn.blog.common.BlogCode;
import com.htn.blog.dto.CommentDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.service.CommentService;
import com.htn.blog.vo.CommentVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable(value = "postId") Long postId,
                                        @Valid @RequestBody CommentDTO commentDTO){
        CommentVO commentVO = commentService.addComment(postId, commentDTO);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Created a new comment successfully!")
                        .data(commentVO)
                        .build()
        );
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<?> getCommentByPostId(@PathVariable(value = "postId") Long postId){
        List<CommentVO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Created a new comment successfully!")
                        .data(comments)
                        .build()
        );
    }

    @GetMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable(value = "postId") Long postId,
                                            @PathVariable(value = "id") Long commentId){
        CommentVO commentVO = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Selected comment successfully!")
                        .data(commentVO)
                        .build()
        );
    }


}
