package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @Column(name = "FILE_URL")
    private String fileUrl;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_ORIGIN_NAME")
    private String fileOriginalName;
    @Column(name = "FILE_TYPE")
    private String fileType;
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    @Column(name = "PUBLIC_ID")
    private String publicId;

    @JsonIgnore
    @OneToMany(mappedBy="fileMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileRelation> fileRelations;
}
