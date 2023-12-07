package com.htn.blog.repository;

import com.htn.blog.entity.FileRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRelationRepository extends JpaRepository<FileRelation, Long> {
    void deleteAllByRelatedIdAndRelatedCode(Long relatedId, String relatedCode);
}
