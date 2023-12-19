package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "TOKEN_TYPE", length = 20)
    private String tokenType;

    @Column(name = "EXPIRATION_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date expirationDate;

    @Column(name = "REFRESH_EXPIRATION_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshExpirationDate;

    @Column(name = "MOBILE_PC", length = 1)
    private String mobilePc;

    @Column(name = "REVOKED", length = 1)
    @Builder.Default
    private String revoked = "N";

    @Column(name = "EXPIRED", length = 1)
    @Builder.Default
    private String expired = "N";

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
