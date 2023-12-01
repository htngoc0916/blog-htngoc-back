package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.PostDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.service.PostService;
import com.htn.blog.vo.PagedResponseVO;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name= "CRUD rest apis for post resource")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Get all post rest api",
            description = "Get all post reset api is used to fetch all the posts from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = BlogConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = BlogConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = BlogConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = BlogConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        PagedResponseVO<PostVO> pagedResponseVO = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get all post successfully!")
                        .data(pagedResponseVO)
                        .build()
        );
    }

    @Operation(summary = "Get post By id rest api",
            description = "Get post by id rest api is used to get single post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "id") Long id){
        PostVO postVO = postService.getPostById(id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get all post successfully!")
                        .data(postVO)
                        .build()
        );
    }

    @Operation(summary = "Get post by category api",
        description = "Get post by category api is used to get all post with category")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getPostsByCategoryId(@PathVariable("id") Long categoryId){
        List<PostVO> postVOList = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get all post successfully!")
                        .data(postVOList)
                        .build()
        );
    }

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
                        .status(BlogConstants.SUCCESS)
                        .message("Created a post successfully!")
                        .data(postVO)
                        .build()
        );
    }

    @Operation(summary = "Update post rest api",
        description = "Update post rest api is used to update a particular post in the database")
    @ApiResponse(responseCode = "200", description = "http status 200 success")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long id){
        PostVO postVO = postService.updatePost(postDTO, id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                .status(BlogConstants.SUCCESS)
                .message("Get all post successfully!")
                .data(postVO)
                .build()
        );
    }

    @Operation(summary = "Delete post rest api",
        description = "Delete post rest api is used to delete a particular post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("deleted post successfully!")
                        .data("")
                        .build()
        );
    }
}
