package com.htn.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private String menuCode;
    private String menuName;
    private Integer parentId;
    private Integer menuOrd;
    private String menuUrl;
    private String menuIcon;

    private String usedYn;
    private Long regId;
    private Long modId;
}
