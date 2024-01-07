package com.htn.blog.dto;

import com.htn.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String accessToken;
    @Builder.Default
    private String tokenType = "Bearer";
    private String refreshToken;
    private Long id;
    private String userName;
    private String email;
    private String avatar;
    private Set<Role> roles;
}
