package com.htn.blog.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private String description;
    private String usedYn;
    private String regId;
    private String modId;
}
