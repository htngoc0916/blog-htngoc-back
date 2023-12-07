package com.htn.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_relation")
@Builder
public class FileRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "RELATED_ID")
    private Long relatedId;
    @Column(name = "RELATED_CODE")
    private String relatedCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_MASTER_ID", nullable = false)
    private FileMaster fileMaster;
}
