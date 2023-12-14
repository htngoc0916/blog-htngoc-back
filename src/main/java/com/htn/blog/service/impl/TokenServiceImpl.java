package com.htn.blog.service.impl;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;
import com.htn.blog.exception.BlogApiException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.TokenRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.security.custom.CustomUserDetailsServiceImpl;
import com.htn.blog.security.jwt.JwtTokenProvider;
import com.htn.blog.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("${blog.jwt-refresh-expiration-milliseconds}")
    private Long refreshExpiration;
    @Value("${blog.jwt-expiration-milliseconds}")
    private Long expiration;

    @Override
    @Transactional
    public Token addTokenToLogin(String token, String loginDevice) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsServiceImpl userDetails = (CustomUserDetailsServiceImpl) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new NotFoundException("User not found with email =" + userDetails.getEmail())
        );

        List<Token> tokens = tokenRepository.findByUser(user);
        if(tokens.size() >= BlogConstants.MAX_AUTH_TOKEN){
            Token tokenToDelete = tokens.stream()
                    .filter(_token -> Objects.equals(_token.getMobilePc(), "Y"))
                    .findFirst()
                    .orElse(tokens.get(0));
            tokenRepository.delete(tokenToDelete);
        }

        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        LocalDateTime refreshExpirationDateTime = LocalDateTime.now().plusSeconds(refreshExpiration);

        //update last login
        user.setLastLoginDt(new Date());
        user = userRepository.save(user);

        return tokenRepository.save(Token.builder()
                            .token(token)
                            .tokenType("Bearer")
                            .expirationDate(expirationDateTime)
                            .refreshToken(UUID.randomUUID().toString())
                            .refreshExpirationDate(refreshExpirationDateTime)
                            .mobilePc(loginDevice)
                            .user(user)
                            .build());
    }

    @Override
    public Token refreshToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new NotFoundException("Refresh token does not exist!")
        );

        if(token.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
            tokenRepository.delete(token);
            throw new BlogApiException("Refresh token was expired. Please make a new login request");
        }

        String newToken = jwtTokenProvider.generateTokenFromUsername(token.getUser().getEmail());
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        LocalDateTime refreshExpirationDateTime = LocalDateTime.now().plusSeconds(refreshExpiration);

        token.setToken(newToken);
        token.setExpirationDate(expirationDateTime);
        token.setRefreshToken(UUID.randomUUID().toString());
        token.setRefreshExpirationDate(refreshExpirationDateTime);

        return token;
    }
}
