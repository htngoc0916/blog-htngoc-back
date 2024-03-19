package com.htn.blog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

    private String contactCode;

    @NotBlank(message = "email is required")
    @NotEmpty(message = "email is required")
    private String email;

    private String fullName;

    private String content;

    @Builder.Default
    private String replyYn = "N";
}
