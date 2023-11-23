package com.htn.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private String status;
    @Builder.Default
    private Date date = new Date();
    private String message;
    private Object data;
}
