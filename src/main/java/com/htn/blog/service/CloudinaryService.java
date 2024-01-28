package com.htn.blog.service;

import com.htn.blog.entity.FileMaster;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    FileMaster uploadCloudinary(Long userId, MultipartFile file);
    void deleteCloudinary(String fileName);
    void deleteCloudinaryById(Long id);
}
