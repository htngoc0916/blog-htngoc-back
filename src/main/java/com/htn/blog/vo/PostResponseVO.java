package com.htn.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseVO {
    private List<PostVO> data;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPage;
    private boolean last;
}
