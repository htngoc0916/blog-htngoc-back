package com.htn.blog.service.impl;

import com.htn.blog.common.UploadCode;
import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.entity.FileRelation;
import com.htn.blog.entity.Role;
import com.htn.blog.entity.User;
import com.htn.blog.exception.MyFileNotFoundException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.repository.FileRelationRepository;
import com.htn.blog.repository.RoleRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.service.CloudinaryService;
import com.htn.blog.service.UserService;
import com.htn.blog.utils.BlogUtils;
import com.htn.blog.utils.FileRelatedCode;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.UserDetailsVO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public PagedResponseVO<User> getAllUser(Integer pageNo, Integer pageSize, String  sortBy, String sortDir, String userName, String usedYn){
        Pageable pageable = BlogUtils.getPageable(sortBy, sortDir, pageNo, pageSize);
        Page<User> resultPage;

        if (StringUtils.hasText(userName) && StringUtils.hasText(usedYn)) {
            resultPage = userRepository.findByUserNameContainingAndUsedYn(userName, usedYn, pageable);
        } else if (StringUtils.hasText(userName)) {
            resultPage = userRepository.findByUserNameContaining(userName, pageable);
        } else if (StringUtils.hasText(usedYn)) {
            resultPage = userRepository.findByUsedYn(usedYn, pageable);
        } else {
            resultPage = userRepository.findAll(pageable);
        }

        List<User> userList = resultPage.getContent();

        return PagedResponseVO.<User>builder()
                .data(userList)
                .pageNo(resultPage.getNumber() + 1)
                .pageSize(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPage(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    @Override
    public UserDetailsVO getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("user not found with id" + id)
        );

        UserDetailsVO userDetailsVO = modelMapper.map(user, UserDetailsVO.class);
        List<FileRelation> fileRelation = fileRelationRepository.findFileRelationByRelatedIdAndRelatedCode(user.getId(), UploadCode.USER.toString());
        if(fileRelation.size() > 0){
            Long imageId = fileRelation.get(0).getFileMaster().getId();
            userDetailsVO.setImageId(imageId);
        }
        return userDetailsVO;
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
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName(userDTO.getRole()).orElseThrow(
                () -> new RuntimeException("ROLE not exists!")
        );
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );
//        handleRelationFiles(userDTO.getImageId(), userId);
        user = user.update(userDTO);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName(userDTO.getRole()).orElseThrow(
                () -> new RuntimeException("ROLE not exists!")
        );
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id = " + userId)
        );

        user.getRoles().clear();
        user.getPosts().clear();
        user = userRepository.save(user);

        fileRelationRepository.deleteAllByRelatedIdAndRelatedCode(userId, FileRelatedCode.USER.toString());
        userRepository.delete(user);
    }

    private void handleRelationFiles(Long imageId, Long userId) {
        Set<FileRelation> fileRelations = new HashSet<>();
        if(imageId != null && imageId > 0) {
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

    @Override
    @Transactional
    public FileMaster uploadAvatar(String email, MultipartFile multipartFile) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("user not found with email: " + email)
        );
        validateImage(multipartFile);
        FileMaster fileMaster = cloudinaryService.uploadCloudinary(user.getId(), multipartFile);

        //update again avatar url
        user.setAvatar(fileMaster.getFileUrl());
        userRepository.save(user);

        //update relation file
        fileRelationRepository.save(FileRelation.builder()
                .fileMaster(fileMaster)
                .relatedId(user.getId())
                .relatedCode(FileRelatedCode.USER.toString())
                .build());

        return fileMaster;
    }

    @Override
    @Transactional
    public void deleteAvatar(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user not found with id: " + userId)
        );

        List<FileRelation> fileRelations = fileRelationRepository.findFileRelationByRelatedIdAndRelatedCode(userId, UploadCode.USER.toString());
        if(fileRelations.size() > 0){
            fileRelations.forEach(fileRelation -> cloudinaryService.deleteCloudinaryByImageId(fileRelation.getFileMaster().getId()));
        }
        fileRelationRepository.deleteAll(fileRelations);

        user.setAvatar("");
        userRepository.save(user);
    }

    private void validateImage(MultipartFile file) {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed.");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds the limit of 5MB.");
        }
    }
}
