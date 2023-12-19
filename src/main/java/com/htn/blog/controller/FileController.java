package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.service.FileMasterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3100")
public class FileController {
    @Autowired
    FileMasterService fileMasterService;

    @Operation(summary = "Upload single file rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/api/v1/files/{userId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@PathVariable("userId") Long userId,
                                        @RequestParam("file") MultipartFile file){
        FileMaster resultFile = fileMasterService.uploadFile(userId, file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Uploaded a new file successfully!")
                        .data(resultFile)
                        .build()
        );
    }
    @Operation(summary = "Upload multiple files rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/api/v1/files/{userId}/uploadMultiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMultipleFiles(@PathVariable("userId") Long userId,
                                                 @RequestParam("file") MultipartFile[] files){
        List<FileMaster> resultFile = fileMasterService.uploadMultipleFiles(userId, files);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Uploaded multiple files successfully!")
                        .data(resultFile)
                        .build()
        );
    }
    @Operation(summary = "Get download link file rest api")
    @GetMapping("/api/v1/files/download/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        Resource resource = fileMasterService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "View image rest api")
    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> viewImage(@PathVariable String fileName) {
        Resource resource = fileMasterService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "delete file rest api")
    @DeleteMapping("/api/v1/files/{fileName:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
        fileMasterService.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Get download file successfully!")
                        .data("")
                        .build()
        );
    }
}
