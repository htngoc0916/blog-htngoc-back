package com.htn.blog.service;

import com.htn.blog.entity.FileMaster;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileMasterService {
    FileMaster uploadFile(MultipartFile file);
    List<FileMaster> uploadMultipleFiles(MultipartFile[] files);
    Resource loadFileAsResource(String fileName);
    void deleteFile(String fileName);
}
