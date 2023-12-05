package com.htn.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMasterVO {
    private String fileUrl;
    private String fileName;
    private String fileOriginName;
    private Long fileSize;
}
