package com.htn.blog.service;

import com.htn.blog.dto.CommentDTO;
import com.htn.blog.entity.Comment;
import com.htn.blog.vo.CommentVO;

import java.util.List;

public interface CommentService {
    CommentVO addComment(Long postId, CommentDTO commentDTO);
    List<CommentVO> getCommentsByPostId(Long postId);
    CommentVO getCommentById(Long postId, Long commentId);
    CommentVO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);
    void deleteComment(Long postId, Long commentId);
}
