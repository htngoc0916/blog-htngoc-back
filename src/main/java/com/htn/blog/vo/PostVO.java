package com.htn.blog.vo;

import com.htn.blog.entity.Category;
import lombok.*;

import java.util.Date;

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
    private Integer viewCnt = 0;

    private String usedYn = "Y";
    private Date regDt;
    private String regId;
    private Date modDt;
    private String modId;
    private Long categoryId;
}
