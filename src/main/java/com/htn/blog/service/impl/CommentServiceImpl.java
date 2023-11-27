package com.htn.blog.service.impl;

import com.htn.blog.dto.CommentDTO;
import com.htn.blog.entity.Comment;
import com.htn.blog.entity.Post;
import com.htn.blog.exception.BlogApiException;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.repository.CommentRepository;
import com.htn.blog.repository.PostRepository;
import com.htn.blog.service.CommentService;
import com.htn.blog.vo.CommentVO;
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
    public CommentVO addComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + postId)
        );

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);

        return modelMapper.map(commentRepository.save(comment), CommentVO.class);
    }

    @Override
    public List<CommentVO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + postId)
        );
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream()
                        .map(comment -> modelMapper.map(comment, CommentVO.class))
                        .toList();
    }

    @Override
    public CommentVO getCommentById(Long postId, Long commentId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found with id = " + commentId)
        );

        if(comment.getPost().getId().equals(postId)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return modelMapper.map(comment, CommentVO.class);
    }
}
