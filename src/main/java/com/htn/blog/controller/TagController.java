package com.htn.blog.controller;

import com.htn.blog.common.BlogCode;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.service.TagService;
import com.htn.blog.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
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

    @PostMapping("/posts/{postId}/tags")
    public ResponseEntity<?> addTag(@PathVariable(value = "postId") Long postId, @RequestBody List<TagDTO> tagDTO){
        PostVO postVO = tagService.addTag(postId, tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Created tags successfully!")
                        .data(postVO)
                        .build()
        );
    }
}
