package com.htn.blog.controller;

import com.htn.blog.common.BlogConstants;
import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.ResponseDTO;
import com.htn.blog.dto.TagDTO;
import com.htn.blog.entity.Tag;
import com.htn.blog.service.TagService;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.PagedResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
@CrossOrigin(origins = "http://localhost:3100")
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private LocalizationUtils localizationUtils;

    @GetMapping
    @Operation(summary = "Get all tags rest api")
    public ResponseEntity<?> getAllTags(
            @RequestParam(value = "pageNo", defaultValue = BlogConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = BlogConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = BlogConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = BlogConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "usedYn", required = false) String usedYn,
            @RequestParam(value = "tagName", required = false) String tagName
    ){
        PagedResponseVO<Tag> tagList = tagService.getAllTag(pageNo, pageSize, sortBy, sortDir, tagName, usedYn);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(MessageKeys.COMMON_ACTIONS_GET_ALL_SUCCESSFULLY)
                        .data(tagList)
                        .build()
        );
    }
    @Operation(summary = "Get tag by tagId rest api")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable(name = "id") Long tagId){
        Tag tag = tagService.getTagById(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_GET_SUCCESSFULLY))
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Create new a tag rest api")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTag(@RequestBody TagDTO tagDTO){
        Tag tag = tagService.addTag(tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_CREATE_SUCCESSFULLY))
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Update tag rest api")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody TagDTO tagDTO){
        Tag tag = tagService.updateTag(tagId, tagDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_SAVE_SUCCESSFULLY))
                        .data(tag)
                        .build()
        );
    }
    @Operation(summary = "Delete tag rest api")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long tagId){
        tagService.deleteTag(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseDTO.builder()
                        .message(localizationUtils.translate(MessageKeys.COMMON_ACTIONS_DELETE_SUCCESSFULLY))
                        .data("")
                        .build()
        );
    }
}
