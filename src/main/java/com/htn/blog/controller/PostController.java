package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.PostDTO;
import com.htn.blog.dto.PostTitleDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.service.PostService;
import com.htn.blog.utils.LocalizationUtils;
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
    @Autowired
    private LocalizationUtils localizationUtils;

    @Operation(summary = "Get all post rest api",
            description = "Get all post reset api is used to fetch all the posts from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping
    public ResponseEntity<?> getAllPosts(@SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                         @PageableDefault Pageable pageable,
                                         @RequestParam(value = "usedYn", required = false) String usedYn,
                                         @RequestParam(value = "postTitle", required = false) String postTitle){
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getAllPosts(pageable, usedYn, postTitle))
                        .build()
        );
    }

    @Operation(summary = "Get post By id rest api",
            description = "Get post by id rest api is used to get single post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostById(id))
                        .build()
        );
    }

    @Operation(summary = "Get hot posts rest api",
            description = "Get hot posts rest api is used to get hot post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/hotPost")
    public ResponseEntity<?> getHotPosts(){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getHotPosts())
                        .build()
        );
    }

    @Operation(summary = "Get post By id rest api",
            description = "Get post by id rest api is used to get single post from the database")
    @ApiResponse(responseCode = "200", description = "Http status 200 success")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getPostBySlug(@PathVariable(name = "slug") String slug){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostBySlug(slug))
                        .build()
        );
    }

    @Operation(summary = "Get related post by slug")
    @GetMapping("/related/slug/{slug}")
    public ResponseEntity<?> getPostsRelatedBySlug(@PathVariable("slug") String slug,
                                            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                            @PageableDefault Pageable pageable){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostsRelatedBySlug(slug, pageable))
                        .build()
        );
    }

    @Operation(summary = "Get post By tag id")
    @GetMapping("/tag/{id}")
    public ResponseEntity<?> getPostByTagId(@PathVariable("id") Long tagId,
                                            @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                            @PageableDefault Pageable pageable){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostsByTag(tagId, pageable))
                        .build()
        );
    }

    @Operation(summary = "Check post title")
    @PutMapping("/checkTitle")
    public ResponseEntity<?> checkPostTitle(@RequestBody PostTitleDTO postTitleDTO){
        boolean response = postService.checkPostTitle(postTitleDTO.getTitle());
        String message = response
                ? localizationUtils.translate(MessageKeys.POST_TITLE_EXIST)
                : localizationUtils.translate(MessageKeys.POST_TITLE_CAN_BE_USED);

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(message)
                        .data(response)
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
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostsByCategory(categoryId, pageable))
                        .build()
        );
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<?> searchPostByTitle(@PathVariable("keywords") String keywords,
                                               @SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                               @PageableDefault Pageable pageable){
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(postService.getPostsByTitle(keywords, pageable))
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
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_CREATE_SUCCESSFULLY))
                        .data(postService.addPost(postDTO))
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
         return ResponseEntity.ok(
                ResponseDTO.builder()
                .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                .data(postService.updatePost(postDTO, id))
                .build()
        );
    }

    @Operation(summary = "Update post view count rest api",
            description = "Update view count rest api is used to update view count of Post by post slug in the database")
    @ApiResponse(responseCode = "200", description = "http status 200 success")
    @PutMapping("/postView/{slug}")
    public ResponseEntity<?> updateViewCount(@PathVariable(name = "slug") String slug){
        postService.updateViewCount(slug);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                        .data("")
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
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_DELETE_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }
}
