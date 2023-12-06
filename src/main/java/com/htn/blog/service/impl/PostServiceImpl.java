package com.htn.blog.service.impl;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.entity.FileMaster;
import com.htn.blog.entity.Post;
import com.htn.blog.entity.Tag;
import com.htn.blog.exception.MyFileNotFoundException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.mapper.FileMasterMapper;
import com.htn.blog.repository.CategoryRepository;
import com.htn.blog.repository.FileMasterRepository;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.PostService;
import com.htn.blog.utils.FileRelatedCode;
import com.htn.blog.vo.FileMasterVO;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.PostVO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private FileMasterMapper fileMasterMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public PostVO addPost(PostDTO postDTO) {
        Category category =  categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found with categoryId = " + postDTO.getCategoryId())
        );

        Set<Tag> tagList = new HashSet<>();
        for(String tagName: postDTO.getTags()){
            Tag tag = tagRepository.findByTagName(tagName);
            if(tag == null){
                tag = tagRepository.save(Tag.builder()
                        .tagName(tagName)
                        .build());
                tag.setRegId(postDTO.getRegId());
            }
            tagList.add(tag);
        }

        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);
        post.setTags(tagList);
        post = postRepository.save(post);

        if(postDTO.getImages() != null && postDTO.getImages().size() > 0){
            fileMasterMapper.updateRelatedFiles(postDTO.getImages(), post.getId(), FileRelatedCode.POST.toString());
        }
        return modelMapper.map(post, PostVO.class);
    }
    @Override
    public PostVO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        PostVO postVO =  modelMapper.map(post, PostVO.class);
        return modelMapper.map(post, PostVO.class);
    }
    @Override
    @Transactional
    public PostVO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found with id = " + postDTO.getCategoryId())
        );

        post = post.update(postDTO);
        post.setCategory(category);
        post = postRepository.save(post);

        //file upload
        fileMasterMapper.deleteRelatedFiles(post.getId(), FileRelatedCode.POST.toString());
        if(postDTO.getImages() != null && postDTO.getImages().size() > 0){
            fileMasterMapper.updateRelatedFiles(postDTO.getImages(), post.getId(), FileRelatedCode.POST.toString());
        }

        return modelMapper.map(post, PostVO.class);
    }
    @Override
    @Transactional
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        fileMasterMapper.deleteRelatedFiles(post.getId(), FileRelatedCode.POST.toString());
        postRepository.delete(post);
    }
    @Override
    public PagedResponseVO<PostVO> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = getSortByDir(sortBy, sortDir);
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        return getPostPaged(postPage);
    }
    @Override
    public PagedResponseVO<PostVO> getPostsByCategory(Long categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with category id = " + categoryId)
        );
        Sort sort = getSortByDir(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postPage = postRepository.findByCategoryId(categoryId, pageable);
        return getPostPaged(postPage);
    }

    @Override
    public PagedResponseVO<PostVO> getPostsByTitle(String keywords, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = getSortByDir(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findByTitleContaining(keywords, pageable);;
        return getPostPaged(postPage);
    }

    @Override
    public PagedResponseVO<PostVO> getPostsByTag(Long tagId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException("Category not found with tag id = " + tagId)
        );
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        Sort sort = getSortByDir(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postPage = postRepository.findByTagsIn(tags, pageable);
        return getPostPaged(postPage);
    }
    private Sort getSortByDir( String sortBy, String sortDir){
        return sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }
    private PagedResponseVO<PostVO> getPostPaged(Page<Post> postPage) {
        List<PostVO> postList = postPage.getContent().stream()
                                        .map(_post -> modelMapper.map(_post, PostVO.class))
                                        .toList();

        return PagedResponseVO.<PostVO>builder()
                .data(postList)
                .pageNo(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPage(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }
}

