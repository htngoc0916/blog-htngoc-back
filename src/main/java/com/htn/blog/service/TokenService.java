package com.htn.blog.service;

import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;

public interface TokenService {
    AuthResponseDTO addTokenToLogin(String token, String loginDevice);
    Token refreshToken(String refreshToken, User user);
}
