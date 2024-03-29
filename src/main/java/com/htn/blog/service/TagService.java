package com.htn.blog.service;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.vo.PagedResponseVO;
import org.springframework.data.domain.Pageable;


public interface TagService {
    Tag getTagById(Long tagId);
    PagedResponseVO<Tag> getAllTag(Pageable pageable, String tagName, String usedYn);
    Tag addTag(TagDTO tagDTO);
    Tag updateTag(Long tagId, TagDTO tagDTO);
    void deleteTag(Long tagId);


}
