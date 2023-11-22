package com.htn.blog.service;

import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
