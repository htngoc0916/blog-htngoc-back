package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.UserDTO;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.entity.User;
import com.htn.blog.service.UserService;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.UserDetailsVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Operation(summary = "Get all user rest api")
    @GetMapping()
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(@SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC)})
                                        @PageableDefault Pageable pageable,
                                        @RequestParam(value = "usedYn", required = false) String usedYn,
                                        @RequestParam(value = "userName", required = false) String userName){
        PagedResponseVO<User> users = userService.getAllUser(pageable, userName, usedYn);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY))
                        .data(users)
                        .build()
        );
    }
    @Operation(summary = "Get user By tag id rest api")
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfo(@PathVariable("id") Long userId){
        UserDetailsVO user = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
                        .data(user)
                        .build()
        );
    }
    @Operation(summary = "Get user By email rest api")
    @GetMapping("/email/{email}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
                        .data(user)
                        .build()
        );
    }

    @Operation(summary = "Check user with email rest api")
    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable("email") String email){
        Boolean result = userService.existsEmail(email);
        String msg = result
                ? localizationUtils.translate(MessageKeys.USER_EMAIL_EXIST)
                : localizationUtils.translate(MessageKeys.USER_EMAIL_CAN_BE_USED);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(msg)
                        .data(result)
                        .build()
        );
    }
    @Operation(summary = "Add new a user rest api")
    @PostMapping()
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        User user = userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_CREATE_SUCCESSFULLY))
                        .data(user)
                        .build()
        );
    }
    @Operation(summary = "Update user rest api")
    @PutMapping(value ="/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId,
                                        @Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                        .data(user)
                        .build()
        );
    }

    @Operation(summary = "Update user rest api")
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_DELETE_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }

    @Operation(summary = "Upload cloudinary rest api")
    @PostMapping("/avatar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadAvatar(@RequestParam(value = "email") String email,
                                          @RequestParam(value = "file") MultipartFile file){
        FileMaster fileMaster = userService.uploadAvatar(email, file);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.USER_UPLOAD_AVATAR_SUCCESSFULLY))
                        .data(fileMaster)
                        .build()
        );
    }
    @Operation(summary = "Upload cloudinary rest api")
    @DeleteMapping("/avatar/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAvatar(@PathVariable("userId") Long userId){
        userService.deleteAvatar(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.USER_DELETE_AVATAR_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }
}
