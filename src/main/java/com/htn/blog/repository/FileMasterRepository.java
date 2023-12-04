package com.htn.blog.repository;

import com.htn.blog.entity.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMasterRepository extends JpaRepository<FileMaster, Long> {
    Optional<FileMaster> findByFileName(String fileName);
}
