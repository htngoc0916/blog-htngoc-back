package com.htn.blog.mapper;

import com.htn.blog.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<PostVO> selectHotPosts();
    List<PostVO> selectRelatedPosts();
}
