package com.htn.blog.service;

import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;
import com.htn.blog.entity.User;

public interface AuthService {
    String login(LoginDTO loginDTO);
    User register(RegisterDTO registerDTO);
}
