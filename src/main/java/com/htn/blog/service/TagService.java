package com.htn.blog.service;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.vo.PostVO;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<Tag> getTagById(Long tagId);
    List<Tag> getAllTag();
    PostVO addTag(Long postId, List<TagDTO> tagDTO);
}
