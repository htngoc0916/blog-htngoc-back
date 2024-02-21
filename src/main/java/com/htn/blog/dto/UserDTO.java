package com.htn.blog.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String userName;
    private String avatar;
    @Builder.Default
    private String usedYn = "Y";
    private Long modId;
    private Long imageId;
    private String role;
}
