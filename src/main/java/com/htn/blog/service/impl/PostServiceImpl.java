package com.htn.blog.service.impl;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.Category;
import com.htn.blog.entity.Post;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.CategoryRepository;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.service.PostService;
import com.htn.blog.vo.PostResponseVO;
import com.htn.blog.vo.PostVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
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
    public PostResponseVO getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public Post updatePost(PostDTO postDTO, Long id) {
        return null;
    }

    @Override
    public void deletePostById(Long id) {

    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        return null;
    }
}

