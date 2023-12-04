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
public class Menu extends BaseEntity {
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

    public Menu update(MenuDTO menuDTO){
        this.setMenuName(menuDTO.getMenuName());
        this.setMenuOrd(menuDTO.getMenuOrd());
        this.setMenuUrl(menuDTO.getMenuUrl());
        this.setUsedYn(menuDTO.getUsedYn());
        this.setModId(menuDTO.getModId());
        this.setModDt(new Date());
        return this;
    }
}