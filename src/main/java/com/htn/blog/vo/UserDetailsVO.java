package com.htn.blog.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.htn.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsVO {
    private Long id;
    private String userName;
    private String email;
    private String avatar;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date lastLoginDt;

    private String usedYn;
    private Long regId;
    private Date regDt;
    private Long modId;
    private Date modDt;

    private Set<Role> roles;
    private Long imageId;
}
