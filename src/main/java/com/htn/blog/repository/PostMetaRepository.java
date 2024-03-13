package com.htn.blog.repository;

import com.htn.blog.entity.PostMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMetaRepository  extends JpaRepository<PostMeta, Long> {
}
