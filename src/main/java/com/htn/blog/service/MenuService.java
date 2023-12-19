package com.htn.blog.service;

import com.htn.blog.dto.MenuDTO;
import com.htn.blog.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    Menu getMenu(Long menuId);
    List<Menu> getAllMenus();
    List<Menu> getMenuByCode(String menuCode);
    Menu addMenu(MenuDTO menuDTO);
    Menu updateMenu(Long menuId, MenuDTO menuDTO);
    void deleteMenu(Long menuId);
}
