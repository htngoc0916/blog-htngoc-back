package com.htn.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @JsonProperty("email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
