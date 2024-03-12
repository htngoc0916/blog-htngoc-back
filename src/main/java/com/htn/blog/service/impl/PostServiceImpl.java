package com.htn.blog.service.impl;

import com.htn.blog.common.MessageKeys;
import com.htn.blog.dto.PostDTO;
import com.htn.blog.entity.*;
import com.htn.blog.exception.NotFoundException;
import com.htn.blog.mapper.PostMapper;
import com.htn.blog.repository.*;
import com.htn.blog.service.PostService;
import com.htn.blog.utils.BlogUtils;
import com.htn.blog.utils.FileRelatedCode;
import com.htn.blog.utils.LocalizationUtils;
import com.htn.blog.vo.PagedResponseVO;
import com.htn.blog.vo.PostVO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileRelationRepository fileRelationRepository;
    @Autowired
    private FileMasterRepository fileMasterRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostMetaRepository postMetaRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LocalizationUtils localizationUtils;
    @Autowired
    private PostMapper postMapper;

    @Override
    @Transactional
    public PostVO addPost(PostDTO postDTO) {
        Category category =  categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.CATEGORY_NOT_FOUND) + " id: " + postDTO.getCategoryId())
                );

        Post post = modelMapper.map(postDTO, Post.class);
        post.getPostMetas().clear();
        post.setCategory(category);
        post.setTags(checkTags(postDTO));
        post = postRepository.save(post);

        //post meta
        Post finalPost = post;
        List<PostMeta> postMetas = postDTO.getPostMetas()
                .stream()
                .map(_postDTO -> PostMeta.builder()
                        .title(_postDTO.getTitle())
                        .slug(_postDTO.getSlug())
                        .post(finalPost)
                        .build())
                .toList();

        postMetaRepository.saveAll(postMetas);
        handleRelationFiles(postDTO.getImages(), post.getId());
        return modelMapper.map(post, PostVO.class);
    }

    @Override
    public PostVO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.POST_NOT_FOUND) + " postId: " + id)
        );
        return modelMapper.map(post, PostVO.class);
    }

    @Override
    public PostVO getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug).orElseThrow(
                () -> new NotFoundException("Post not found with slug = " + slug)
        );
        return modelMapper.map(post, PostVO.class);
    }

    @Override
    public boolean checkPostTitle(String title) {
        return postRepository.existsByTitle(title);
    }

    @Override
    public PagedResponseVO<PostVO> getPostsRelatedBySlug(String slug, Pageable pageable) {
        Post post = postRepository.findBySlug(slug).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.POST_NOT_FOUND) + " slug: " + slug)
        );

        Set<Tag> postTags = post.getTags();
        Page<Post> relatedPostsPage = postRepository.findRelatedPosts(post.getId(), postTags, pageable);

        return getPostPaged(relatedPostsPage);
    }


    @Override
    public List<PostVO> getHotPosts() {
        return postMapper.selectHotPosts();
    }

    @Override
    @Transactional
    public PostVO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Post not found with id = " + id)
        );
        Category category = categoryRepository.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.CATEGORY_NOT_FOUND) +  " categoryId: " + postDTO.getCategoryId())
        );

        //handle post images
        handleRelationFiles(postDTO.getImages(), post.getId());

        post = post.update(postDTO);
        post.setCategory(category);
        post.setTags(checkTags(postDTO));

        //delete meta
        Post finalPost = post;
        postMetaRepository.deleteByPost_Id(post.getId());
        List<PostMeta> postMetas = postDTO.getPostMetas().stream()
                .map((meta)-> PostMeta.builder()
                        .title(meta.getTitle())
                        .slug(meta.getSlug())
                        .post(finalPost)
                        .build())
                .toList();
        post.setPostMetas(postMetas);

        post = postRepository.save(post);
        return modelMapper.map(post, PostVO.class);
    }

    @Override
    public void updateViewCount(String slug) {
        Post post = postRepository.findBySlug(slug).orElseThrow(
            () -> new NotFoundException(localizationUtils.translate(MessageKeys.POST_NOT_FOUND) + " slug: " + slug)
        );
        post.setViewCnt(post.getViewCnt() + 1L);
        postRepository.save(post);
    }

    private Set<Tag> checkTags(PostDTO postDTO) {
        Set<Tag> tags = new HashSet<>();
        if(postDTO.getTags() != null){
            for(String tagName: postDTO.getTags()){
                Tag tag = tagRepository.findByTagName(tagName);
                if(tag == null){
                    tag = tagRepository.save(Tag.builder()
                            .tagName(tagName)
                            .build());
                    tag.setRegId(postDTO.getRegId());
                }
                tags.add(tag);
            }
        }
        return tags;
    }
    private void handleRelationFiles(Set<Long> imagesId, Long postId) {
        Set<FileRelation> fileRelations = new HashSet<>();
        if(imagesId != null) {
            for(Long imageId : imagesId){
                FileMaster fileMaster = fileMasterRepository.findById(imageId).orElseThrow(
                    () -> new NotFoundException(localizationUtils.translate(MessageKeys.POST_NOT_FOUND) + " imageId: " + imageId)
                );
                fileRelations.add(FileRelation.builder()
                        .fileMaster(fileMaster)
                        .relatedId(postId)
                        .relatedCode(FileRelatedCode.POST.toString())
                        .build());
            }
        }
        fileRelationRepository.deleteAllByRelatedIdAndRelatedCode(postId, FileRelatedCode.POST.toString());
        if(fileRelations.size() > 0){
            fileRelationRepository.saveAll(fileRelations);
        }
    }

    @Override
    @Transactional
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.POST_NOT_FOUND) + " id: " + id)
        );
        fileRelationRepository.deleteAllByRelatedIdAndRelatedCode(post.getId(), FileRelatedCode.POST.toString());
        postRepository.delete(post);
    }
    @Override
    public PagedResponseVO<PostVO> getAllPosts(Pageable pageable, String usedYn, String postTitle) {
        BlogUtils.validatePageable(pageable);

        Page<Post> resultPage;
        if(StringUtils.hasText(postTitle) && StringUtils.hasText(usedYn)){
            resultPage = postRepository.findByTitleContainingAndUsedYn(postTitle, usedYn, pageable);
        }else if(StringUtils.hasText(postTitle)){
            resultPage = postRepository.findByTitleContaining(postTitle, pageable);
        }else if(StringUtils.hasText(usedYn)){
            resultPage = postRepository.findByUsedYn(usedYn, pageable);
        }else {
            resultPage = postRepository.findAll(pageable);
        }

        return getPostPaged(resultPage);
    }
    @Override
    public PagedResponseVO<PostVO> getPostsByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.CATEGORY_NOT_FOUND) + " categoryId: " + categoryId)
        );

        Page<Post> postPage = postRepository.findByCategoryId(categoryId, pageable);
        return getPostPaged(postPage);
    }

    @Override
    public PagedResponseVO<PostVO> getPostsByTitle(String keywords, Pageable pageable) {
        Page<Post> postPage = postRepository.findByTitleContaining(keywords, pageable);;
        return getPostPaged(postPage);
    }

    @Override
    public PagedResponseVO<PostVO> getPostsByTag(Long tagId, Pageable pageable) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new NotFoundException(localizationUtils.translate(MessageKeys.TAG_NOT_FOUND) +  " id: " + tagId)
        );
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        Page<Post> postPage = postRepository.findByTagsIn(tags, pageable);
        return getPostPaged(postPage);
    }

    private PagedResponseVO<PostVO> getPostPaged(Page<Post> postPage) {
        List<PostVO> postList = postPage.getContent()
                                        .stream()
                                        .map(_post -> modelMapper.map(_post, PostVO.class))
                                        .toList();

        return PagedResponseVO.<PostVO>builder()
                .data(postList)
                .pageNo(BlogUtils.resultPageNo(postPage))
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPage(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }
}

