package com.htn.blog.service;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.vo.PagedResponseVO;

public interface TagService {
    Tag getTagById(Long tagId);
    PagedResponseVO<Tag> getAllTag(Integer pageNo, Integer pageSize, String  sortBy, String sortDir, String tagName, String usedYn);
    Tag addTag(TagDTO tagDTO);
    Tag updateTag(Long tagId, TagDTO tagDTO);
    void deleteTag(Long tagId);
}
