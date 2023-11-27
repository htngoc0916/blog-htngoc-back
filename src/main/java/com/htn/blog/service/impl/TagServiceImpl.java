package com.htn.blog.service.impl;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Post;
import com.htn.blog.entity.Tag;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.TagService;
import com.htn.blog.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Optional<Tag> getTagById(Long tagId) {
        return Optional.empty();
    }

    @Override
    public List<Tag> getAllTag() {
        List<Tag> tagList = tagRepository.findAll();
        return tagList;
    }

    @Override
    public PostVO addTag(Long postId, List<TagDTO> tagDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with postId = " + postId)
        );
//
//        tagDTO.stream().map(_tagDTO -> {
//
//        })

//        Tag tag = tagRepository.findById(tagDTO.getId())
//                .orElseThrow(
//                () -> new NotFoundException("Tag not found with tagId = " + tagDTO.getId())
//        );


        return null;
    }
}
