package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.service.CategoryService;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.PagedResponseVO;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/categories")
@CrossOrigin(origins = "http://localhost:3100")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Operation(summary = "Get all category rest api")
    @GetMapping()
    public ResponseEntity<?> getAllCategory(@SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                            @PageableDefault Pageable pageable,
                                            @RequestParam(value = "usedYn", required = false) String usedYn,
                                            @RequestParam(value = "categoryName", required = false) String categoryName){
        PagedResponseVO<Category> categoryList = categoryService.getAllCategories(pageable, categoryName, usedYn);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
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
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
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
                            .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_CREATE_SUCCESSFULLY))
                            .data(category)
                            .build()
        );
    }
    @Operation(summary = "Update category rest api")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long categoryId){
        Category category = categoryService.updateCategory(categoryDTO, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                        .data(category)
                        .build()
        );
    }
    @Operation(summary = "Delete category rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_DELETE_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }
}
