package com.htn.blog.service.impl;

import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.User;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.mapper.FileMasterMapper;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.service.UserService;
import com.htn.blog.utils.FileRelatedCode;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileMasterMapper fileMasterMapper;
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
    @Transactional
    public User updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
        fileMasterMapper.deleteRelatedFiles(user.getId(), FileRelatedCode.USER.toString());
        if(userDTO.getImage() != null){
            fileMasterMapper.updateRelatedFiles(List.of(userDTO.getImage()), user.getId(), FileRelatedCode.USER.toString());
        }
        user = user.update(userDTO);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
        fileMasterMapper.deleteRelatedFiles(user.getId(), FileRelatedCode.USER.toString());
        userRepository.delete(user);
    }
}
