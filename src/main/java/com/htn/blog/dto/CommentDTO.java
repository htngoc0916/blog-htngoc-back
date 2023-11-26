package com.htn.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    @Schema(description = "Post comment name")
    @NotEmpty(message = "Comment name should not be null or empty")
    private String commentName;

    @Schema(description = "Post comment email")
    @NotEmpty(message = "Comment email should not be null or empty")
    @Email
    private String commentEmail;

    @Schema(description = "Post comment content")
    private String content;

    @Schema(description = "Blog post use flag")
    @Size(max = 1, message = "Post usedYn only allows 1 character")
    private String usedYn;

    @Schema(description = "Blog post created user")
    private String regId;

    @Schema(description = "Blog post modify user")
    private String modId;

    @Schema(description = "Blog post id")
    private Long postId;
}
