package com.htn.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_master")
@Builder
public class FileMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RELATED_ID")
    private Integer relatedId;

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

    @Column(name = "USED_YN")
    private String usedYn;

    @Builder.Default
    @Column(name = "REG_DT")
    private Date regDt = new Date();

    @Column(name = "REG_ID")
    private String regId;

    @Builder.Default
    @Column(name = "MOD_DT")
    private Date modDt = new Date();

    @Column(name = "MOD_ID")
    private String modId;
}
