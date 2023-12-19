package com.htn.blog.controller;

import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cloudinary")
@CrossOrigin(origins = "http://localhost:3100")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Operation(summary = "Upload cloudinary rest api")
    @PostMapping("/{userId}/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadCloudinary(@PathVariable("userId") Long userId,
                                              @RequestParam("file")MultipartFile file){
        FileMaster fileMaster = cloudinaryService.uploadCloudinary(userId, file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Upload file successfully!")
                        .data(fileMaster)
                        .build()
        );
    }

    @Operation(summary = "Upload cloudinary rest api")
    @DeleteMapping("/delete/{fileName:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCloudinary(@PathVariable("fileName") String fileName){
        cloudinaryService.deleteCloudinary(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message("Delete file successfully!")
                        .data("")
                        .build()
        );
    }
}
