package com.htn.blog.service;

import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(CategoryDTO categoryDTO);
    Category getCategory(Long categoryId);
    List<Category> getAllCategories();
    Category updateCategory(Long id);
    void deleteCategory(Long categoryId);
}
