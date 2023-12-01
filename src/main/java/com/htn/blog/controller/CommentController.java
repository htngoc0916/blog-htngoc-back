package com.htn.blog.controller;


import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.CommentDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Comment;
import com.htn.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get comments by postId rest api")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<?> getCommentByPostId(@PathVariable(value = "postId") Long postId){
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Created a new comment successfully!")
                        .data(comments)
                        .build()
        );
    }
    @Operation(summary = "Get comment by commentId rest api")
    @GetMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable(value = "postId") Long postId,
                                            @PathVariable(value = "id") Long commentId){
        Comment comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Selected comment successfully!")
                        .data(comment)
                        .build()
        );
    }
    @Operation(summary = "Create new a comment rest api")
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable(value = "postId") Long postId,
                                        @Valid @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.addComment(postId, commentDTO);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Created a new comment successfully!")
                        .data(comment)
                        .build()
        );
    }
    @Operation(summary = "Update comment rest api")
    @PutMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(value = "postId") Long postId,
                                           @PathVariable(value = "id") Long commentId,
                                           @Valid @RequestBody CommentDTO commentDTO){
        Comment comment = commentService.updateCommentById(postId, commentId, commentDTO);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Updated comment successfully!")
                        .data(comment)
                        .build()
        );
    }
    @Operation(summary = "Delete comment by commentId rest api")
    @DeleteMapping("/post/{postId}/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
                                           @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Deleted comment successfully!")
                        .data("")
                        .build()
        );
    }
}
