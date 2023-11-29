package com.htn.blog.service.impl;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.entity.Post;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.CategoryRepository;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.repository.TagRepository;
import com.htn.blog.service.PostService;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.PostVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostVO addPost(PostDTO postDTO) {
        Category category =  categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found with categoryId = " + postDTO.getCategoryId())
        );

        Post post = modelMapper.map(postDTO, Post.class);
        post.setCategory(category);
        return modelMapper.map(postRepository.save(post), PostVO.class);
    }

    @Override
    public PagedResponseVO<PostVO> getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostVO> postVOList = postPage.getContent().stream().map(post -> modelMapper.map(post, PostVO.class)).toList();

        return PagedResponseVO.<PostVO>builder()
                .data(postVOList)
                .pageNo(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPage(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }


    @Override
    public PostVO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );

        return modelMapper.map(post, PostVO.class);
    }

    @Override
    public PostVO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found with id = " + postDTO.getCategoryId())
        );

        post = post.update(postDTO);
        post.setCategory(category);
        return modelMapper.map(postRepository.save(post), PostVO.class);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        postRepository.delete(post);
    }

    @Override
    public List<PostVO> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category not found with category id = " + categoryId)
        );

        List<Post> postList = postRepository.findByCategoryId(categoryId);
        return postList.stream().map(post -> modelMapper.map(post, PostVO.class)).toList();
    }
}

