package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.htn.blog.dto.PostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = "TITLE")})
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "SLUG")
    private String slug;
    @Column(name = "VIEW_CNT")
    private Long viewCnt;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "POST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post update(PostDTO postDTO){
        this.setTitle(postDTO.getTitle());
        this.setDescription(postDTO.getDescription());
        this.setContent(postDTO.getContent());
        this.setSlug(postDTO.getSlug());
        this.setModId(postDTO.getModId());
        this.setModDt(new Date());
        this.setUsedYn(postDTO.getUsedYn());
        return this;
    }
}
