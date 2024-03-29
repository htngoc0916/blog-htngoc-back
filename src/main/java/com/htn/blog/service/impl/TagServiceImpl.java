package com.htn.blog.service.impl;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.TagService;
import com.htn.blog.utils.BlogUtils;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.PagedResponseVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocalizationUtils localizationUtils;

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.TAG_NOT_FOUND + " id = " + tagId))
        );
    }

    @Override
    public PagedResponseVO<Tag> getAllTag(Pageable pageable, String tagName, String usedYn) {
        BlogUtils.validatePageable(pageable);

        Page<Tag> resultPage;
        if (StringUtils.hasText(tagName) && StringUtils.hasText(usedYn)) {
            resultPage = tagRepository.findByTagNameContainingAndUsedYn(tagName, usedYn, pageable);
        } else if (StringUtils.hasText(tagName)) {
            resultPage = tagRepository.findByTagNameContaining(tagName, pageable);
        } else if (StringUtils.hasText(usedYn)) {
            resultPage = tagRepository.findByUsedYn(usedYn, pageable);
        } else {
            resultPage = tagRepository.findAll(pageable);
        }

        List<Tag> tagList = resultPage.getContent()
                .stream()
                .map(_tag -> modelMapper.map(_tag, Tag.class))
                .toList();

        return PagedResponseVO.<Tag>builder()
                .data(tagList)
                .pageNo(BlogUtils.resultPageNo(resultPage))
                .pageSize(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPage(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    @Override
    public Tag addTag(TagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long tagId, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.TAG_NOT_FOUND + " id = " + tagId))
        );
        tag = tag.update(tagDTO);

        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.TAG_NOT_FOUND + " id = " + tagId))
        );
        tagRepository.delete(tag);
    }
}
