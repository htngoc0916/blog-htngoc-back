package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.dto.AuthResponseDTO;
import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.entity.Token;
import com.htn.blog.entity.User;
import com.htn.blog.service.AuthService;
import com.htn.blog.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3100")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request){
        String token = authService.login(loginDTO);
        String loginDevice = getLoginDevice(request.getHeader("User-Agent"));
        AuthResponseDTO result = tokenService.addTokenToLogin(token, loginDevice);
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .message("login successfully")
                                            .data(result)
                                            .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private String getLoginDevice(String header) {
        return header.toLowerCase().contains("mobile") ? "Y" : "N";
    }

    // Build Register REST API
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .message("register successfully")
                .data(authService.register(registerDTO))
                .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(){

        return null;
    }

}
