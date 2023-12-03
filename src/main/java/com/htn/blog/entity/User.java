package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.htn.blog.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USER_NAME",nullable = false)
    private String userName;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "AVATAR")
    private String avatar;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "USED_YN", length = 1)
    @Builder.Default
    private String usedYn = "Y";
    @Column(name = "LAST_LOGIN_DT")
    private Date lastLoginDt;

    @Column(name = "REG_DT")
    @Builder.Default
    private Date regDt = new Date();
    @Column(name = "REG_ID")
    private String regId;
    @Column(name = "MOD_DT")
    private Date modDt;
    @Column(name = "MOD_ID")
    private String modId;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    public User update(UserDTO userDTO){
        this.setUserName(userDTO.getUserName());
        this.setAvatar(userDTO.getAvatar());
        this.setModId(userDTO.getModId());
        this.setUsedYn(userDTO.getUsedYn());
        return this;
    }
}
