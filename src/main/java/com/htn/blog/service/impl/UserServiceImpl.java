package com.htn.blog.service.impl;

import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.User;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @Override
    public User getUserInfo(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("user not found with id" + id)
        );
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
        user = user.update(userDTO);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
       userRepository.delete(user);
    }
}
