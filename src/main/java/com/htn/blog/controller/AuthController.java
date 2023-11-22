package com.htn.blog.controller;

import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;
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
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        String token = authService.login(loginDTO);
        return new ResponseEntity<>(AuthResponseDTO.builder().accessToken(token).build()
                                    , HttpStatus.OK);
    }
    // Build Register REST API
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String respone = authService.register(registerDTO);
        return new ResponseEntity<>(respone, HttpStatus.CREATED);
    }
}
