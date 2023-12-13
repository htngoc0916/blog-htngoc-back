package com.htn.blog.service.impl;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.TokenRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.security.custom.CustomUserDetailsServiceImpl;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    UserRepository userRepository;

    @Value("${blog.jwt-refresh-expiration-milliseconds}")
    private Long refreshExpiration;
    @Value("${blog.jwt-expiration-milliseconds}")
    private Long expiration;

    @Override
    @Transactional
    public AuthResponseDTO addTokenToLogin(String token, String loginDevice) {

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
        Token saveToken = tokenRepository.save(Token.builder()
                                        .token(token)
                                        .tokenType("Bearer")
                                        .expirationDate(expirationDateTime)
                                        .refreshToken(UUID.randomUUID().toString())
                                        .refreshExpirationDate(refreshExpirationDateTime)
                                        .mobilePc(loginDevice)
                                        .user(user)
                                        .build());

        return AuthResponseDTO.builder()
                .accessToken(token)
                .refreshToken(saveToken.getRefreshToken())
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Token refreshToken(String refreshToken, User user) {
        return null;
    }
}
