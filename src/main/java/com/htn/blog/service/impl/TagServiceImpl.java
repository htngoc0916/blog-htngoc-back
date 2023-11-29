package com.htn.blog.service.impl;

import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Tag updateTag(Long tagId, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Tag not found with tag id = " + tagId)
        );
        tag = tag.update(tagDTO);

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Tag not found with tag id = " + tagId)
        );
        tagRepository.delete(tag);
    }
}
