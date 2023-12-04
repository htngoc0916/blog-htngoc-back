package com.htn.blog.utils;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.exception.BlogApiException;
import org.springframework.http.HttpStatus;

public class BlogUtils {
    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
        }

        if (size > BlogConstants.MAX_PAGE_SIZE) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + BlogConstants.MAX_PAGE_SIZE);
        }
    }
}
