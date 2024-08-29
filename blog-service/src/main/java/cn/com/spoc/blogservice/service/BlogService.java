package cn.com.spoc.blogservice.service;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.dto.hot.AuthorInfoDTO;
import cn.com.spoc.blogservice.entity.AttentionEntity;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.ColumnEntity;
import cn.com.spoc.blogservice.entity.TagEntity;
import cn.com.spoc.blogservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private ColumnRepository columnRepository;
    @Autowired
    private AttentionService attentionService;
    @Autowired
    private HotService hotService;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private HotRepository hotRepository;
    @Autowired
    private AttentionRepository attentionRepository;

    public void saveBlog(BlogEntity blogEntity, List<TagEntity> tagEntities) {
        var columnEntity = blogEntity.getBlogColumn();
        var blogEntity1 = blogRepository.findBlogEntitiesByBlogTitle(blogEntity.getBlogTitle());
        if (blogEntity1 != null) {
            return;
        }
        var existingColumn = columnRepository.findByColumnName(columnEntity.getColumnName());
        blogEntity.setBlogColumn(existingColumn);
        if (tagEntities != null) {
            var persistedTags = new ArrayList<TagEntity>();
            for (var tag : tagEntities) {
                var existingTag = tagRepository.findByTagName(tag.getTagName());
                if (existingTag != null) {
                    persistedTags.add(existingTag);
                } else {
                    var newTag = tagRepository.save(tag);
                    persistedTags.add(newTag);
                }
            }
            blogEntity.setTags(persistedTags);
        }
        var newBlogEntity = blogRepository.save(blogEntity);
        existingColumn.getBlogEntityLists().add(newBlogEntity);
    }

    public BlogEntity getBlog(String blogTitle) {
        return blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
    }

    public List<BlogDTO> getAllBlogs() {
        var blogDTOS = blogRepository.getAllBlogs();
        if (blogDTOS == null) {
            return null;
        }
        for (var blogDTO : blogDTOS) {
            var blogEntity = blogRepository.findBlogEntitiesByBlogTitle(blogDTO.getBlogTitle());
            int likeNum = hotRepository.BlogAllLike(blogEntity);
            int favoriteNum = hotRepository.BlogAllFavorite(blogEntity);
            blogDTO.setLikeNum(likeNum);
            blogDTO.setFavoriteNum(favoriteNum);
        }
        return blogDTOS;
    }

    public List<BlogDTO> getAttentionBlogs(String username) {
        List<String> authors = attentionRepository.getAttentionAuthor(username);
        var blogDTOS = new ArrayList<BlogDTO>();
        for (var author : authors) {
            var blogEntityList = blogRepository.getBlogEntitiesByAuthor(author);
            for (var blogEntity : blogEntityList) {
                var blogDTO = new BlogDTO(
                        blogEntity.getBlogTitle(),
                        blogEntity.getAuthor(),
                        blogEntity.getBlogContent(),
                        blogEntity.getBlogCover()
                );
                blogDTOS.add(blogDTO);
            }
        }
        return blogDTOS;
    }

    public List<BlogDTO> getFavoriteBlogs(String username) {
        return hotRepository.getFavoriteBlogs(username);
    }

    public List<BlogDTO> getColumn(String column) {
        var columnEntity = columnRepository.findByColumnName(column);
        if (columnEntity == null) {
            columnEntity = new ColumnEntity();
        }
        return blogRepository.getByColumn(columnEntity);
    }

    public List<String> getRecommendBlogs() {
        var blogEntityList = blogRepository.getAllBlogs();
        var TopFiveBlog = blogEntityList.stream()
                .sorted(Comparator.comparing(BlogDTO::getLikeNum).reversed())
                .limit(5)
                .toList();
        var blogTitles = new ArrayList<String>();
        for (BlogDTO blogDTO : TopFiveBlog) {
            String blogTitle = blogDTO.getBlogTitle();
            blogTitles.add(blogTitle);
        }
        return blogTitles;
    }

    public AuthorInfoDTO getAuthor(String username, String blogTitle) {
        var blogEntity = this.getBlog(blogTitle);
        var authorInfoDTO = new AuthorInfoDTO();
        var author = blogEntity.getAuthor();
        authorInfoDTO.setAvatar(null);
        authorInfoDTO.setUsername(author);
        if (attentionService.getIsAttention(username, author) == null) {
            attentionRepository.save(new AttentionEntity(username, author));
        }
        authorInfoDTO.setAttention(attentionService.getIsAttention(username, author));
        authorInfoDTO.setArticleNum(blogRepository.countBlogsByAuthor(author));
        authorInfoDTO.setLikeNum(hotService.getLikeCount(author));
        authorInfoDTO.setAttended(attentionService.getAttentionNum(author));
        return authorInfoDTO;
    }

    public AuthorInfoDTO getUser(String username) {
        AuthorInfoDTO authorInfoDTO = new AuthorInfoDTO();
        authorInfoDTO.setAvatar(null);
        authorInfoDTO.setUsername(username);
        authorInfoDTO.setArticleNum(blogRepository.countBlogsByAuthor(username));
        authorInfoDTO.setLikeNum(hotService.getLikeCount(username));
        authorInfoDTO.setAttended(attentionService.getAttentionNum(username));
        return authorInfoDTO;
    }
}
