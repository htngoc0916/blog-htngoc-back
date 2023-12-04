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
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;
    @Column(name = "DESCRIPTION")
    private String description;

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
