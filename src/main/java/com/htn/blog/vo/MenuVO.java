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
public class MenuVO {
    private Long id;
    private String menuCode;
    private String menuName;
    private Integer parentId;
    private Integer menuOrder;
    private String menuUrl;
    private String menuIcon;
    private String usedYn;
    private List<MenuVO> children;
}
