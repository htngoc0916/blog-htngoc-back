package com.htn.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
@Builder
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONTACT_CODE")
    private String contactCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "REPLY_YN")
    private String replyYn;
}
