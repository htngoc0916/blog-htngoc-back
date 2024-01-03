package com.htn.blog.repository;

import com.htn.blog.entity.Category;
import com.htn.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByUsernameOrEmail(String username, String email);
//    Optional<User> findByUserName(String username);
//    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findByUserNameContainingAndUsedYn(String userName, String usedYn, Pageable pageable);
    Page<User> findByUserNameContaining(String userName, Pageable pageable);
    Page<User> findByUsedYn(String usedYn, Pageable pageable);
}
