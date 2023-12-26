package com.htn.blog.service.impl;

import com.htn.blog.dto.CategoryDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.CategoryRepository;
import com.htn.blog.service.CategoryService;
import com.htn.blog.utils.BlogUtils;
import com.htn.blog.vo.PagedResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with id = " + categoryId)
        );
    }

    @Override
    public PagedResponseVO<Category> getAllCategories(Integer pageNo, Integer pageSize, String  sortBy, String sortDir) {
        Pageable pageable = BlogUtils.getPageable(sortBy, sortDir, pageNo, pageSize);
        Page<Category> resultPage = categoryRepository.findAll(pageable);

        List<Category> categoryList = resultPage.getContent()
                                            .stream()
                                            .map(_category -> modelMapper.map(_category, Category.class))
                                            .toList();

        return PagedResponseVO.<Category>builder()
                .data(categoryList)
                .pageNo(resultPage.getNumber() + 1)
                .pageSize(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPage(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();

    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with id = " + categoryId)
        );
        category = category.update(categoryDTO);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with id = " + categoryId)
        );
        categoryRepository.delete(category);
    }
}
