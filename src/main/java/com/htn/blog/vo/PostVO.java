package com.htn.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVO {

    private Long id;
    private String title;
    private String description;
    private String content;
    private String slug;
    private String thumbnail;
    private Integer viewCnt = 0;

    private String usedYn = "Y";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date regDt;
    private String regId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date modDt;
    private String modId;
    private Long categoryId;
    private Set<TagVO> tags;
    private UserVO user;
}
