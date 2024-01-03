package com.htn.blog.repository;

import com.htn.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByCategoryNameContainingAndUsedYn(String categoryName, String usedYn, Pageable pageable);
    Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);
    Page<Category> findByUsedYn(String usedYn, Pageable pageable);
}
