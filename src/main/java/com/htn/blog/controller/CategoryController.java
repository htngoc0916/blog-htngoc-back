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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long categoryId){
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Search category successfully!")
                        .data(category)
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategory(){
        List<Category> categoryList = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Search all category successfully!")
                        .data(categoryList)
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                            .status(BlogCode.SUCCESS)
                            .message("Created new a category.")
                            .data(category)
                            .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long categoryId){
        Category category = categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("Updated category successfully!")
                        .data(category)
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .status(BlogCode.SUCCESS)
                        .message("deleted category successfully!")
                        .data("")
                        .build()
        );
    }
}
