package com.htn.blog.service;

import com.htn.blog.dto.CommentDTO;
import com.htn.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long postId, CommentDTO commentDTO);
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long postId, Long commentId);
    Comment updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);
    void deleteComment(Long postId, Long commentId);
}
