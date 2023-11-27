package com.htn.blog.service;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.vo.PostResponseVO;
import com.htn.blog.vo.PostVO;

import java.util.List;

public interface PostService {
    PostVO addPost(PostDTO postDTO);
    PostResponseVO getAllPosts(Integer pageNo, Integer pageSize, String  sortBy, String sortDir);
    PostVO getPostById(Long id);
    PostVO updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);
    List<PostVO> getPostsByCategory(Long categoryId);
}
