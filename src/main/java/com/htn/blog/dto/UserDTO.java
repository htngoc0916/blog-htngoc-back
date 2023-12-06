package com.htn.blog.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String userName;
    private String avatar;
    private String usedYn;
    private String modId;
    private Long image;
}
