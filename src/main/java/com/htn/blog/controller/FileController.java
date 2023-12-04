package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.service.FileMasterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    FileMasterService fileMasterService;
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        FileMaster resultFile = fileMasterService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Get post by id successfully!")
                        .data(resultFile)
                        .build()
        );
    }
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return null;
    }
}
