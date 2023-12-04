package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.htn.blog.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "COMMENT_NAME")
    private String commentName;
    @Column(name = "COMMENT_EMAIL")
    private String commentEmail;
    @Column(name = "CONTENT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    @JsonIgnore
    private Post post;

    public Comment update(CommentDTO commentDTO){
        this.setCommentName(commentDTO.getCommentName());
        this.setCommentEmail(commentDTO.getCommentEmail());
        this.setContent(commentDTO.getContent());
        this.setUsedYn(commentDTO.getUsedYn());
        this.setModDt(new Date());
        this.setModId(commentDTO.getModId());
        return this;
    }
}
