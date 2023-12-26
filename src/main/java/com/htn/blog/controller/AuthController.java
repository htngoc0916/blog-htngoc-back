package com.htn.blog.controller;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.*;
import com.htn.blog.entity.Token;
import com.htn.blog.service.AuthService;
import com.htn.blog.service.TokenService;
import com.htn.blog.utils.LocalizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3100")

public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private LocalizationUtils localizationUtils;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login REST API
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request){
        String token = authService.login(loginDTO);
        String loginDevice = getLoginDevice(request.getHeader("User-Agent"));
        Token resultToken = tokenService.addTokenToLogin(token, loginDevice);
        return ResponseEntity.ok(ResponseDTO.builder()
                                            .message(localizationUtils.translate(MessageKeys.AUTH_LOGIN_SUCCESSFULLY))
                                            .data(mappingToAuthResponse(resultToken))
                                            .build());
    }

    private String getLoginDevice(String header) {
        return header.toLowerCase().contains("mobile") ? "Y" : "N";
    }

    // Build Register REST API
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        ResponseDTO responseDTO = ResponseDTO.builder()
                                            .message("register successfully ")
                                            .data(authService.register(registerDTO))
                                            .build();
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO){
        Token resultToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken());
        return ResponseEntity.ok(ResponseDTO.builder()
                                            .message("Refresh token successfully")
                                            .data(mappingToAuthResponse(resultToken))
                                            .build());
    }

    private AuthResponseDTO mappingToAuthResponse(Token resultToken){
        return AuthResponseDTO.builder()
                .accessToken(resultToken.getToken())
                .refreshToken(resultToken.getRefreshToken())
                .id(resultToken.getUser().getId())
                .userName(resultToken.getUser().getUserName())
                .email(resultToken.getUser().getEmail())
                .avatar(resultToken.getUser().getAvatar())
                .roles(resultToken.getUser().getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toSet()))
                .build();
    }
}
