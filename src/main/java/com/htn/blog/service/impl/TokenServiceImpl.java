package com.htn.blog.service.impl;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.common.MessageKeys;
import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.exception.TokenRefreshException;
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
                () -> new NotFoundException(MessageKeys.USER_NOT_FOUND_EMAIL + ": " + userDetails.getEmail())
        );

        List<Token> tokens = tokenRepository.findByUser(user);
        if(tokens.size() >= BlogConstants.MAX_AUTH_TOKEN){
            Token tokenToDelete = tokens.stream()
                    .filter(_token -> Objects.equals(_token.getMobilePc(), "Y"))
                    .findFirst()
                    .orElse(tokens.get(0));
            tokenRepository.delete(tokenToDelete);
        }

        Date curentDate = new Date();
        Date expirationDateTime = new Date(curentDate.getTime() + expiration);
        Date refreshExpirationDateTime = new Date(curentDate.getTime() + refreshExpiration);

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
                () -> new TokenRefreshException(refreshToken, MessageKeys.AUTH_REFRESH_TOKEN_DOEST_EXIST)
        );

        if(token.getRefreshExpirationDate().compareTo(new Date()) < 0){
            tokenRepository.delete(token);
            throw new TokenRefreshException(refreshToken, MessageKeys.AUTH_REFRESH_TOKEN_EXPIRED);
        }

        String newToken = jwtTokenProvider.generateTokenFromUsername(token.getUser().getEmail());
        Date curentDate = new Date();
        Date expirationDateTime = new Date(curentDate.getTime() + expiration);
        Date refreshExpirationDateTime = new Date(curentDate.getTime() + refreshExpiration);

        token.setToken(newToken);
        token.setExpirationDate(expirationDateTime);
        token.setRefreshToken(refreshToken);
        token.setRefreshExpirationDate(refreshExpirationDateTime);

        return token;
    }
}
