package com.htn.blog.service;

import com.htn.blog.dto.MenuDTO;
import com.htn.blog.entity.Menu;
import com.htn.blog.vo.MenuVO;

import java.util.List;

public interface MenuService {
    Menu getMenu(Long menuId);
//    List<Menu> getAllMenus();
    List<MenuVO> getAllMenus();
    List<MenuVO> getMenuByCode(String menuCode);
    Menu addMenu(MenuDTO menuDTO);
    Menu updateMenu(Long menuId, MenuDTO menuDTO);
    void deleteMenu(Long menuId);
}
