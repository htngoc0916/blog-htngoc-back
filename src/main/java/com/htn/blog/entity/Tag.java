package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.htn.blog.dto.TagDTO;
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
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "TAG_NAME", nullable = false, unique = true)
    private String tagName;
    @Column(name = "COLOR")
    private String color;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public Tag update(TagDTO tagDTO){
        this.setTagName(tagDTO.getTagName());
        this.setColor(tagDTO.getColor());
        this.setUsedYn(tagDTO.getUsedYn());
        this.setRegId(tagDTO.getRegId());
        this.setModDt(new Date());
        this.setModId(tagDTO.getModId());
        return this;
    }
}
