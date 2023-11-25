package com.htn.blog.service;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.Post;
import com.htn.blog.vo.PostResponseVO;
import com.htn.blog.vo.PostVO;

import java.util.List;

public interface PostService {
    PostVO addPost(PostDTO postDTO);
    PostResponseVO getAllPosts(Integer pageNo, Integer pageSize, String  sortBy, String sortDir);
    Post getPostById(Long id);
    Post updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);
    List<Post> getPostsByCategory(Long categoryId);
}
