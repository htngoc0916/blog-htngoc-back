package com.htn.blog.repository;

import com.htn.blog.entity.Category;
import com.htn.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTagName(String tagName);

    Page<Tag> findByTagNameContainingAndUsedYn(String tagName, String usedYn, Pageable pageable);
    Page<Tag> findByTagNameContaining(String tagName, Pageable pageable);
    Page<Tag> findByUsedYn(String usedYn, Pageable tagName);
}
