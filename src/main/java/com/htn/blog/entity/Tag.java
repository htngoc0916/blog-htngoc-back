package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TAG_NAME", nullable = false, unique = true)
    private String tagName;
    @Column(name = "COLOR")
    private String color;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "tags")
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    @Builder.Default
    @Column(name = "USED_YN", length = 1)
    private String usedYn = "Y";
    @Builder.Default
    @Column(name = "REG_DT")
    private Date regDt = new Date();
    @Column(name = "REG_ID")
    private String regId;
    @Column(name = "MOD_DT")
    private Date modDt;
    @Column(name = "MOD_ID")
    private String modId;
}
