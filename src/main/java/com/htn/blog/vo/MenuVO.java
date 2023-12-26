package com.htn.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {
    private Long id;
    private String menuCode;
    private String menuName;
    private Integer menuOrder;
    private String menuUrl;
    private String menuIcon;
    private List<MenuVO> submenus;
}
