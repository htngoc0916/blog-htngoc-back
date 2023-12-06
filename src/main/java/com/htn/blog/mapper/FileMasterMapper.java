package com.htn.blog.mapper;

import com.htn.blog.entity.FileMaster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMasterMapper {
    List<FileMaster> getRelatedFiles(List<FileMaster> fileMasters, String relatedCode);
    void deleteRelatedFile(Long relatedId, String relatedCode);
    void updateRelatedFiles(Long relatedId, String relatedCode);
}
