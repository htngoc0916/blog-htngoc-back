package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.service.CategoryService;
import com.htn.blog.vo.PagedResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:3100")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all category rest api")
    @GetMapping()
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "pageNo", defaultValue = BlogConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = BlogConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                            @RequestParam(value = "sortBy", defaultValue = BlogConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                            @RequestParam(value = "sortDir", defaultValue = BlogConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
                                            @RequestParam(value = "usedYn", required = false) String usedYn,
                                            @RequestParam(value = "categoryName", required = false) String categoryName){
        PagedResponseVO<Category> categoryList = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortDir, categoryName, usedYn);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Search all category successfully!")
                        .data(categoryList)
                        .build()
        );
    }
    @Operation(summary = "Get category by categoryId rest api")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long categoryId){
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Search category successfully!")
                        .data(category)
                        .build()
        );
    }
    @Operation(summary = "Add new a category rest api")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                            .message("Created new a category.")
                            .data(category)
                            .build()
        );
    }
    @Operation(summary = "Update category rest api")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long categoryId){
        Category category = categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message("Updated category successfully!")
                        .data(category)
                        .build()
        );
    }
    @Operation(summary = "Delete category rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message("deleted category successfully!")
                        .data("")
                        .build()
        );
    }
}
