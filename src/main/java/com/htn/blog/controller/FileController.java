package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.service.FileMasterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    FileMasterService fileMasterService;

    @Operation(summary = "Upload single file rest api")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        FileMaster resultFile = fileMasterService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Uploaded a new file successfully!")
                        .data(resultFile)
                        .build()
        );
    }
    @Operation(summary = "Upload multiple files rest api")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files){
        List<FileMaster> resultFile = fileMasterService.uploadMultipleFiles(files);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .status(BlogConstants.SUCCESS)
                        .message("Uploaded multiple files successfully!")
                        .data(resultFile)
                        .build()
        );
    }
    @Operation(summary = "Upload file rest api")
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        Resource resource = fileMasterService.loadFileAsResource(fileName);
        return null;
    }
}
