package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    @Operation(summary = "Get all tags rest api")
    public ResponseEntity<?> getAllTags(){
        List<Tag> tagList = tagService.getAllTag();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Get all post successfully!")
                        .data(tagList)
                        .build()
        );
    }
    @Operation(summary = "Get tag by tagId rest api")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable(name = "id") Long tagId){
        Tag tag = tagService.getTagById(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Get post by id successfully!")
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Create new a tag rest api")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTag(@RequestBody TagDTO tagDTO){
        Tag tag = tagService.addTag(tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Created tags successfully!")
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Update tag rest api")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody TagDTO tagDTO){
        Tag tag = tagService.updateTag(tagId, tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Updated tag successfully!")
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Delete tag rest api")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long tagId){
        tagService.deleteTag(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Deleted tag successfully!")
                        .data("")
                        .build()
        );
    }
}
