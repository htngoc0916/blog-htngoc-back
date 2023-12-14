package com.htn.blog.service.impl;

import com.htn.blog.common.RoleConstants;
import com.htn.blog.dto.LoginDTO;
import com.htn.blog.dto.RegisterDTO;
import com.htn.blog.entity.Role;
import com.htn.blog.entity.User;
import com.htn.blog.exception.BlogApiException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.RoleRepository;
import com.htn.blog.repository.UserRepository;
import com.htn.blog.security.custom.CustomUserDetailsServiceImpl;
import com.htn.blog.security.jwt.JwtTokenProvider;
import com.htn.blog.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetailsServiceImpl userDetails = (CustomUserDetailsServiceImpl) authentication.getPrincipal();
        return jwtTokenProvider.generateJwtToken(userDetails);

//        return jwtTokenProvider.generateToken(authentication);
    }

    private Authentication authenticate(String userEmail, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEmail, password);
        try {
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch(BadCredentialsException ex) {
            log.error("Invalid email or password");
            throw new BlogApiException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public User register(RegisterDTO registerDTO) {
        //check for email exists in database
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BlogApiException("Email is already exists!");
        }

        User user = User.builder()
                .userName(registerDTO.getUserName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
        user.setRegId(registerDTO.getRegId());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByRoleName(RoleConstants.ROLE_USER.toString()).orElseThrow(
                () -> new RuntimeException("ROLE_USER not exists!")
        );
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
