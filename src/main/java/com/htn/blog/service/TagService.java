package com.htn.blog.service;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag getTagById(Long tagId);
    List<Tag> getAllTag();
    Tag addTag(TagDTO tagDTO);
    Tag updateTag(Long tagId, TagDTO tagDTO);
    void deleteTag(Long tagId);
}
