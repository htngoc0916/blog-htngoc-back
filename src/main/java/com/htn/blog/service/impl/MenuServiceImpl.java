package com.htn.blog.service.impl;

import com.htn.blog.dto.MenuDTO;
import com.htn.blog.entity.Menu;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.MenuRepository;
import com.htn.blog.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Menu addMenu(MenuDTO menuDTO) {
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException("Menu not found width id = " + menuId)
        );
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu updateMenu(Long menuId, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException("Menu not found width id = " + menuId)
        );
        menu = menu.update(menuDTO);
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException("Menu not found width id = " + menuId)
        );
        menuRepository.delete(menu);
    }
}
