package com.htn.blog.service.impl;

import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.entity.FileRelation;
import com.htn.blog.entity.User;
import com.htn.blog.exception.MyFileNotFoundException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.mapper.FileMasterMapper;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.repository.FileRelationRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.service.UserService;
import com.htn.blog.utils.FileRelatedCode;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileRelationRepository fileRelationRepository;
    @Autowired
    private FileMasterRepository fileMasterRepository;

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
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("user not found with email = " + email)
        );
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("user not found with email = " + email)
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
        handleRelationFiles(userDTO.getImage(), userId);
        user = user.update(userDTO);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
        fileRelationRepository.deleteAllByRelatedIdAndRelatedCode(userId, FileRelatedCode.USER.toString());
        userRepository.delete(user);
    }

    private void handleRelationFiles(Long imageId, Long userId) {
        Set<FileRelation> fileRelations = new HashSet<>();
        if(imageId != null) {
            FileMaster fileMaster = fileMasterRepository.findById(imageId).orElseThrow(
                    () -> new MyFileNotFoundException("Post image not found with imageId = " + imageId)
            );
            fileRelations.add(FileRelation.builder()
                    .fileMaster(fileMaster)
                    .relatedId(userId)
                    .relatedCode(FileRelatedCode.USER.toString())
                    .build());
        }
        fileRelationRepository.deleteAllByRelatedIdAndRelatedCode(userId, FileRelatedCode.USER.toString());
        if(fileRelations.size() > 0){
            fileRelationRepository.saveAll(fileRelations);
        }
    }
}
