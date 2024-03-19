package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.MenuDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Menu;
import com.htn.blog.service.MenuService;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Operation(summary = "Get all menus rest api")
    @GetMapping
    public ResponseEntity<?> getAllMenus(){
        List<MenuVO> menuVO = menuService.getAllMenus();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(menuVO)
                        .build()
        );
    }
    @Operation(summary = "Get menu by menuId rest api")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMenu(@PathVariable("id") Long menuId){
        Menu menu = menuService.getMenu(menuId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
                        .data(menu)
                        .build()
        );
    }

    @Operation(summary = "Get menu by menuCode rest api")
    @GetMapping("/menu-code/{code}")
    public ResponseEntity<?> getMenuByCode(@PathVariable("code") String menuCode){
        List<MenuVO> menu = menuService.getMenuByCode(menuCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
                        .data(menu)
                        .build()
        );
    }

    @Operation(summary = "Add new a menu rest api")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMenu(@Valid @RequestBody MenuDTO menuDTO){
        Menu menu = menuService.addMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_CREATE_SUCCESSFULLY))
                        .data(menu)
                        .build()
        );
    }
    @Operation(summary = "Update a menu rest api")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMenu(@PathVariable(name = "id") Long menuId, @RequestBody MenuDTO menuDTO){
        Menu menu = menuService.updateMenu(menuId, menuDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                        .data(menu)
                        .build()
        );
    }
    @Operation(summary = "Delete a menu rest api")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMenu(@PathVariable(name = "id") Long menuId){
        menuService.deleteMenu(menuId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_DELETE_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }
}
