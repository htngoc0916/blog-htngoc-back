package com.htn.blog.repository;

import com.htn.blog.entity.Post;
import com.htn.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCategoryId(Long id, Pageable pageable);
    Page<Post> findByTagsIn(Set<Tag> tags, Pageable pageable);
    Page<Post> findByTitleContaining(String keywords, Pageable pageable);
}
