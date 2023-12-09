package com.htn.blog.dto;

import com.htn.blog.common.BlogConstants;
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
    @Builder.Default
    private String status = BlogConstants.SUCCESS;
    @Builder.Default
    private Date date = new Date();
    private String message;
    private Object data;
}
