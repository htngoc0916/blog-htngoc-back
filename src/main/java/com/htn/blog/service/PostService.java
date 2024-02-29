package com.htn.blog.service;

import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.Post;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.PostVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostVO addPost(PostDTO postDTO);
    PagedResponseVO<PostVO> getAllPosts(Pageable pageable, String usedYn, String postTitle);
    PostVO getPostById(Long id);
    PostVO getPostBySlug(String slug);
    PostVO updatePost(PostDTO postDTO, Long id);
    void updateViewCount(String slug);
    void deletePostById(Long id);
    PagedResponseVO<PostVO> getPostsByCategory(Long categoryId, Pageable pageable);
    PagedResponseVO<PostVO> getPostsRelatedBySlug(String slug, Pageable pageable);
    PagedResponseVO<PostVO> getPostsByTitle(String keywords, Pageable pageable);
    PagedResponseVO<PostVO> getPostsByTag(Long tagId, Pageable pageable);

    boolean checkPostTitle(String title);
    List<PostVO> getHotPosts();
}
