package com.htn.blog.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {
    private Long id;
    private String tagName;
    private String color;
    private String usedYn = "Y";
    private Long regId;
    private Long modId;
}
