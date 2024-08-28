package cn.com.spoc.blogservice.service;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.dto.hot.AttentionInfoDTO;
import cn.com.spoc.blogservice.dto.hot.AuthorInfoDTO;
import cn.com.spoc.blogservice.entity.AttentionEntity;
import cn.com.spoc.blogservice.repository.AttentionRepository;
import cn.com.spoc.blogservice.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttentionService {
    @Autowired
    private AttentionRepository attentionRepository;
    @Autowired
    private BlogRepository blogRepository;

    public Boolean getIsAttention(String me, String other) {
        return attentionRepository.getIsAttention(me, other);
    }

    public int getAttentionNum(String author) {
        return attentionRepository.getAttentionNum(author);
    }

    public void change(String me, String other, boolean attention) {
        var attentionEntity = attentionRepository.getAttentionEntitiesByMe(me, other);
        if (attentionEntity == null) {
            attentionEntity = new AttentionEntity(me, other);
            attentionRepository.save(attentionEntity);
        }
        attentionEntity.setAttention(attention);
        attentionRepository.save(attentionEntity);
    }

    public List<AttentionInfoDTO> getMyAttention(String username) {
        return attentionRepository.getMyAttention(username);
    }

    public List<AttentionInfoDTO> getAttentionMe(String other) {
        return attentionRepository.getAttentionMe(other);
    }

    public AuthorInfoDTO getAttentionUserInfo(String username) {
        var attention = attentionRepository.getAttentionNum(username);
        var articleNum = getAttentionBlogs(username).size();
        return new AuthorInfoDTO(articleNum, attention);
    }

    public List<BlogDTO> getAttentionBlogs(String username) {
        var authors = attentionRepository.getAttentionAuthor(username);
        var blogDTOS = new ArrayList<BlogDTO>();
        for (var author : authors) {
            var blogEntityList = blogRepository.getBlogEntitiesByAuthor(author);
            for (var blogEntity : blogEntityList) {
                var blogDTO = new BlogDTO(
                        blogEntity.getBlogTitle(),
                        blogEntity.getAuthor(),
                        blogEntity.getBlogContent(),
                        null
                );
                blogDTOS.add(blogDTO);
            }
        }
        return blogDTOS;
    }
}
