package com.htn.blog.controller;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name= "CRUD rest apis for post resource")
@CrossOrigin(origins = "http://localhost:3100")
public class PostController {
    @Autowired
    private PostService postService;

    @Operation(summary = "Get all post rest api",
            description = "Get all post reset api is used to fetch all the posts from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping
    public ResponseEntity<?> getAllPosts(@SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                         @PageableDefault Pageable pageable,
                                         @RequestParam(value = "usedYn", required = false) String usedYn,
                                         @RequestParam(value = "postTitle", required = false) String postTitle){
        PagedResponseVO<PostVO> pagedResponseVO = postService.getAllPosts(pageable, usedYn, postTitle);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
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
                        .message("Get post By Id successfully!")
                        .data(postVO)
                        .build()
        );
    }

    @Operation(summary = "Get post By id rest api",
            description = "Get post by id rest api is used to get single post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getPostBySlug(@PathVariable(name = "slug") String slug){
        PostVO postVO = postService.getPostBySlug(slug);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Get post By Slug successfully!")
                        .data(postVO)
                        .build()
        );
    }

    @Operation(summary = "Get related post by slug")
    @GetMapping("/related/slug/{slug}")
    public ResponseEntity<?> getPostsRelatedBySlug(@PathVariable("slug") String slug,
                                            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                            @PageableDefault Pageable pageable){

        PagedResponseVO<PostVO> pagedResponseVO = postService.getPostsRelatedBySlug(slug, pageable);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Get post with category Id successfully!")
                        .data(pagedResponseVO)
                        .build()
        );
    }

    @Operation(summary = "Get post By tag id")
    @GetMapping("/tag/{id}")
    public ResponseEntity<?> getPostByTagId(@PathVariable("id") Long tagId,
                                            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                            @PageableDefault Pageable pageable){

        PagedResponseVO<PostVO> pagedResponseVO = postService.getPostsByTag(tagId, pageable);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Get post with category Id successfully!")
                        .data(pagedResponseVO)
                        .build()
        );
    }

    @Operation(summary = "Get post by category api",
        description = "Get post by category api is used to get all post with category")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getPostsByCategoryId(
            @PathVariable("id") Long categoryId,
            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
            @PageableDefault Pageable pageable){
        PagedResponseVO<PostVO> pagedResponseVO = postService.getPostsByCategory(categoryId, pageable);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Get post with category Id successfully!")
                        .data(pagedResponseVO)
                        .build()
        );
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<?> searchPostByTitle(@PathVariable("keywords") String keywords,
                                               @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                               @PageableDefault Pageable pageable){
        PagedResponseVO<PostVO> pagedResponseVO = postService.getPostsByTitle(keywords, pageable);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Get posts with title successfully!")
                        .data(pagedResponseVO)
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
                        .message("deleted post successfully!")
                        .data("")
                        .build()
        );
    }
}
