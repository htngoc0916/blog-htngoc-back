package com.htn.blog.vo;

import com.htn.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {
    private long id;
    private String commentName;
    private String commentEmail;
    private String content;
    private String usedYn = "Y";
    private Date regDt = new Date();
    private String regId;
    private Date modDt;
    private String modId;
    private Post post;
}
