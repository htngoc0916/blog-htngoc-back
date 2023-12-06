package com.htn.blog.mapper;

import com.htn.blog.entity.FileMaster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMasterMapper {
    List<FileMaster> getRelatedFiles(List<Long> fileIds, String relatedCode);
    void deleteRelatedFiles(Long relatedId, String relatedCode);
    void updateRelatedFiles(List<Long> fileId, Long relatedId, String relatedCode);
}
