package com.htn.blog.controller;

import com.htn.blog.common.BlogCode;
import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                            .status(BlogCode.SUCCESS)
                            .message("Created new a category")
                            .data(category)
                            .build()
        );
    }
}
