package com.htn.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_master")
@Builder
public class FileMaster extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "RELATED_ID")
    private Long relatedId;
    @Column(name = "RELATED_CODE")
    private String relatedCode;

    @Column(name = "FILE_URL")
    private String fileUrl;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_ORIGIN_NAME")
    private String fileOriginName;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "FILE_SIZE")
    private Long fileSize;
}
