package com.htn.blog.utils;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.exception.BlogApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public class BlogUtils {
    public static void validatePageable(Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        if (page < 0) {
            throw new BlogApiException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BlogApiException("Size number cannot be less than zero.");
        }

        if (size > BlogConstants.MAX_PAGE_SIZE) {
            throw new BlogApiException("Page size must not be greater than " + BlogConstants.MAX_PAGE_SIZE);
        }
    }

    public static String generateFileName() {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return String.join("_", timestamp, uuid);
    }

    public static int resultPageNo(Page<?> page) {
        return page.getNumber() + 1;
    }
}
