package com.htn.blog.service;

import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.vo.PagedResponseVO;

public interface CategoryService {
    Category addCategory(CategoryDTO categoryDTO);
    Category getCategory(Long categoryId);
    PagedResponseVO<Category> getAllCategories(Integer pageNo, Integer pageSize, String  sortBy, String sortDir, String categoryName, String usedYn);
    Category updateCategory(CategoryDTO categoryDTO, Long categoryId);
    void deleteCategory(Long categoryId);
}
