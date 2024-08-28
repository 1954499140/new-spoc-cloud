package cn.com.spoc.blogservice.service;

import cn.com.spoc.blogservice.dto.hot.HotInfoDTO;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.HotEntity;
import cn.com.spoc.blogservice.repository.BlogRepository;
import cn.com.spoc.blogservice.repository.HotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotService {
    @Autowired
    private HotRepository hotRepository;
    @Autowired
    private BlogRepository blogRepository;

    public HotInfoDTO getHotInfoByUsername(String username, BlogEntity blogTitle) {
        return hotRepository.getHotInfoDTO(username, blogTitle);
    }

    public long getLikeCount(String username) {
        return hotRepository.getLikeCount(username);
    }

    public void changLike(String username, BlogEntity blogEntity, boolean isLiked) {
        var hotEntity = hotRepository.getHotEntity(username, blogEntity);
        hotEntity.setLiked(isLiked);
        hotRepository.save(hotEntity);
    }

    public void changeFavorite(String username, BlogEntity blogEntity, boolean favorite) {
        var hotEntity = hotRepository.getHotEntity(username, blogEntity);
        hotEntity.setFavorite(favorite);
        hotRepository.save(hotEntity);
    }

    public void saveHotEntity(String username, String blogTitle) {
        var blogEntity = getBlog(blogTitle);
        var hotEntity = hotRepository.getHotEntity(username, blogEntity);
        if (hotEntity == null) {
            hotEntity = new HotEntity(username, blogEntity);
            hotRepository.save(hotEntity);
        }
    }

    public BlogEntity getBlog(String blogTitle) {
        return blogRepository.findBlogEntitiesByBlogTitle(blogTitle);
    }
}
