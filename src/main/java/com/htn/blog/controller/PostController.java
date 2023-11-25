package com.htn.blog.controller;

import com.htn.blog.common.BlogCode;
import com.htn.blog.dto.PostDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Post;
import com.htn.blog.service.PostService;
import com.htn.blog.vo.PostVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name= "CRUD rest apis for post resource")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Create post Rest Api",
            description = "Create post rest api is used to save post into database")
    @ApiResponse(responseCode = "201", description = "Http status 201 CREATED")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addPost(@Valid @RequestBody PostDTO postDTO){
        PostVO postVO = postService.addPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Created a post successfully!")
                        .data(postVO)
                        .build()
        );
    }
}
