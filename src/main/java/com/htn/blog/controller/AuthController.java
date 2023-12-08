package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);
        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                                                        .accessToken(token)
                                                        .build();
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .status(BlogConstants.SUCCESS)
                                            .message("login successfully")
                                            .data(authResponseDTO)
                                            .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    // Build Register REST API
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .status(BlogConstants.SUCCESS)
                .message("register successfully")
                .data(authService.register(registerDTO))
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
