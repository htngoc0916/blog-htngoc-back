package com.htn.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostMetaDTO {
    private Long id;

    @Schema(description = "Post meta title")
    @NotEmpty(message = "Post meta title should not be null or empty")
    private String title;

    @Schema(description = "Post meta slug")
    @NotEmpty(message = "Post meta slug should not be null or empty")
    private String slug;

    @Schema(description = "Post meta remark")
    private String remark;

    @Schema(description = "Blog post id")
    private Long postId;
}
