package com.htn.blog.repository;

import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
}
