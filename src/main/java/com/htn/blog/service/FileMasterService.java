package com.htn.blog.service;

import com.htn.blog.entity.FileMaster;
import org.springframework.web.multipart.MultipartFile;

public interface FileMasterService {
    FileMaster uploadFile(MultipartFile file);
}
