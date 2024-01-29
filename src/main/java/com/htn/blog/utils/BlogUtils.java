package com.htn.blog.utils;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.exception.BlogApiException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.UUID;

public class BlogUtils {
    public static void validatePageNumberAndSize(int page, int size) {
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
    public static Sort getSortByDir(String sortBy, String sortDir){
        return sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    public static Pageable getPageable(String sortBy, String sortDir, Integer pageNo, Integer pageSize){
        Sort sort = BlogUtils.getSortByDir(sortBy, sortDir);
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
}
