package com.htn.blog.controller;

import com.htn.blog.common.BlogCode;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.service.TagService;
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

    @GetMapping()
    public ResponseEntity<?> getAllTags(){
        List<Tag> tagList = tagService.getAllTag();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Get all post successfully!")
                        .data(tagList)
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable(name = "id") Long tagId){
        Tag tag = tagService.getTagById(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Get post by id successfully!")
                        .data(tag)
                        .build()
        );
    }
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTag(@RequestBody TagDTO tagDTO){
        Tag tag = tagService.addTag(tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Created tags successfully!")
                        .data(tag)
                        .build()
        );
    }

}
