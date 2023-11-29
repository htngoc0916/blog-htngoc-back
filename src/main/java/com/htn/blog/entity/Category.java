package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.htn.blog.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;
    @Column(name = "DESCRIPTION")
    private String description;

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

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public Category update(CategoryDTO categoryDTO){
        this.setCategoryName(categoryDTO.getCategoryName());
        this.setDescription(categoryDTO.getDescription());
        this.setUsedYn(categoryDTO.getUsedYn());
        this.setModId(categoryDTO.getModId());
        this.setModDt(new Date());
        return this;
    }
}
