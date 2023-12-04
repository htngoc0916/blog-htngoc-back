package com.htn.blog.service.impl;

import com.htn.blog.dto.CommentDTO;
import com.htn.blog.entity.Comment;
import com.htn.blog.entity.Post;
import com.htn.blog.exception.BlogApiException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.CommentRepository;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Comment addComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + postId)
        );
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        findPost(postId);
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long postId, Long commentId){
        findPost(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found with id = " + commentId)
        );
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }

    @Override
    public Comment updateCommentById(Long postId, Long commentId, CommentDTO commentDTO) {
        findPost(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found with id = " + commentId)
        );
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comment = comment.update(commentDTO);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        findPost(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found with id = " + commentId)
        );
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }

    private void findPost(Long postId){
        postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + postId)
        );
    }
}
