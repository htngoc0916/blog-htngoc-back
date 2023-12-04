package com.htn.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonIgnoreProperties(
        value = { "regId", "modId", "usedYn", "modDt", "regDt" },
        allowGetters = true
)
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "USED_YN", length = 1)
    private String usedYn;
    @Column(name = "REG_DT")
    private Date regDt;

    @Column(name = "REG_ID")
    @CreatedBy
    private String regId;

    @Column(name = "MOD_DT")
    private Date modDt;

    @LastModifiedBy
    @Column(name = "MOD_ID")
    private String modId;

    @PrePersist
    protected void onCreate(){
        usedYn = "Y";
        regDt = new Date();
    }

    @PreUpdate
    protected void onUpload(){
        modDt = new Date();
    }
}
