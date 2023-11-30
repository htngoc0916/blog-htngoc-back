package com.htn.blog.entity;

import com.htn.blog.dto.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MENU_CODE")
    private String menuCode;
    @Column(name = "MENU_NAME")
    private String menuName;
    @Column(name = "PARENT_ID")
    private Integer parentId;
    @Column(name = "MENU_ORD")
    private Integer menuOrd;
    @Column(name = "MENU_URL")
    private String menuUrl;

    @Builder.Default
    @Column(name = "USED_YN", length = 1)
    private String usedYn = "Y";
    @Builder.Default
    @Column(name = "REG_DT")
    private Date regDt = new Date();
    @Column(name = "REG_ID")
    private String regId;
    @Column(name = "MOD_DT")
    private Date modDt;
    @Column(name = "MOD_ID")
    private String modId;

    public Menu update(MenuDTO menuDTO){
        this.setMenuName(menuDTO.getMenuName());
        this.setMenuOrd(menuDTO.getMenuOrd());
        this.setMenuUrl(menuDTO.getMenuUrl());
        return this;
    }
}