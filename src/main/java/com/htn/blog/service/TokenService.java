package com.htn.blog.service;

import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.entity.Token;

public interface TokenService {
    Token addTokenToLogin(String token, String loginDevice);
    Token refreshToken(String refreshToken);
}
