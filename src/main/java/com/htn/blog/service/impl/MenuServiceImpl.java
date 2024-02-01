package com.htn.blog.service.impl;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.MenuDTO;
import com.htn.blog.entity.Menu;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.MenuRepository;
import com.htn.blog.service.MenuService;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.MenuVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Override
    public Menu addMenu(MenuDTO menuDTO) {
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.MENU_NOT_FOUND + " id = " + menuId))
        );
    }

    @Override
    public List<MenuVO> getMenuByCode(String menuCode) {
        List<Menu> allMenus = menuRepository.findByMenuCodeAndUsedYnOrderByMenuOrdAsc(menuCode, "Y");
        return getMenuResponse(allMenus);
    }

    @Override
    public Menu updateMenu(Long menuId, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.MENU_NOT_FOUND + " id = " + menuId))
        );
        menu = menu.update(menuDTO);
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.MENU_NOT_FOUND + " id = " + menuId))
        );
        menuRepository.delete(menu);
    }


    public List<MenuVO> getAllMenus() {
        List<Menu> allMenus = menuRepository.findAll();
        return getMenuResponse(allMenus);
    }

    private List<MenuVO> getMenuResponse(List<Menu> allMenus) {
        List<Menu> rootMenus = findRootMenus(allMenus);

        List<MenuVO> menuVOList = new ArrayList<>();
        for (Menu rootMenu : rootMenus) {
            MenuVO menuVO = convertToMenuVO(rootMenu, allMenus);
            menuVOList.add(menuVO);
        }

        return menuVOList;
    }

    private List<Menu> findRootMenus(List<Menu> allMenus) {
        return allMenus.stream().filter(menu -> menu.getParentId() == null || menu.getParentId() == 0).toList();
    }

    private MenuVO convertToMenuVO(Menu rootMenu, List<Menu> allMenus) {
        MenuVO menuVO = modelMapper.map(rootMenu, MenuVO.class);

        List<Menu> children = findAllChildMenus(rootMenu.getId(), allMenus);
        List<MenuVO> childVOs = new ArrayList<>();
        for (Menu child : children) {
            MenuVO childVO = convertToMenuVO(child, allMenus);
            childVOs.add(childVO);
        }

        menuVO.setChildren(childVOs);

        return menuVO;
    }

    private List<Menu> findAllChildMenus(Long rootId, List<Menu> allMenus) {
        return allMenus.stream().filter(menu -> Objects.equals(menu.getParentId(), rootId)).toList();
    }
}
