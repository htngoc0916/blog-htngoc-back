package com.htn.blog.service.impl;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Tag not found with tag id = " + tagId)
        );
    }

    @Override
    public List<Tag> getAllTag() {
        List<Tag> tagList = tagRepository.findAll();
        return tagList.stream()
                    .map(tag -> modelMapper.map(tag, Tag.class))
                    .toList();
    }

    @Override
    public Tag addTag(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        return tagRepository.save(tag);
    }
}
