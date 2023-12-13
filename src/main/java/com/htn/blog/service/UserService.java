package com.htn.blog.service;

import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User getUserInfo(Long id);
    User getUserByEmail(String email);
    boolean existsEmail(String email);
    User addUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
}
