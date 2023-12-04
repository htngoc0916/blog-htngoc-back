package com.htn.blog.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponseDTO {
    private String fileUrl;
    private String fileName;
    private String fileOriginName;
    private String fileType;
    private Long fileSize;
}
