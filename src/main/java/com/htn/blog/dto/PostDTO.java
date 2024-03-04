package com.htn.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "PostDTO model Information")
@Builder
public class PostDTO {
    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(description = "Blog post description")
    private String description;

    @Schema(description = "Blog post content")
    private String content;

    @Schema(description = "Blog post slug")
    private String slug;

    @Schema(description = "Blog post thumbnail id")
    private Long thumbnailId;

    @Schema(description = "Blog post thumbnail")
    private String thumbnail;

    @Schema(description = "Blog post use flag")
    @Size(max = 1, message = "Post usedYn only allows 1 character")
    private String usedYn;

    @Schema(description = "Blog post author")
    private Long userId;

    @Schema(description = "Blog post created user")
    private Long regId;

    @Schema(description = "Blog post modify user")
    private Long modId;

    @Schema(description = "Blog category id")
    private Long categoryId;

    @Schema(description = "Blog post view count")
    @Builder.Default
    private Long viewCnt = 0L;

    @Schema(description = "Blog category id")
    private Set<String> tags;

    @Schema(description = "Blog images id")
    private Set<Long> images;
}
