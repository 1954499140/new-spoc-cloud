package cn.com.spoc.blogservice.repository;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.dto.hot.HotInfoDTO;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.HotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotRepository extends JpaRepository<HotEntity, String> {
    @Query("SELECT new cn.com.spoc.blogservice.dto.hot.HotInfoDTO(h.liked, h.favorite) FROM HotEntity h WHERE h.username = :username AND h.blogTitle = :blogTitle")
    HotInfoDTO getHotInfoDTO(@Param("username") String username, @Param("blogTitle") BlogEntity blogTitle);

    @Query("SELECT h FROM HotEntity h WHERE h.username = :username AND h.blogTitle = :blogTitle")
    HotEntity getHotEntity(@Param("username") String username, @Param("blogTitle") BlogEntity blogTitle);

    @Query("SELECT COUNT(h) FROM HotEntity h WHERE h.blogTitle IN (SELECT b FROM BlogEntity b WHERE b.author = :username) AND h.liked = true")
    long getLikeCount(@Param("username") String username);

    @Query("SELECT COUNT(h) FROM HotEntity h WHERE h.blogTitle = :blogEntity AND h.liked = true")
    int BlogAllLike(@Param("blogEntity") BlogEntity blogEntity);

    @Query("SELECT COUNT(h) FROM HotEntity h WHERE h.blogTitle = :blogEntity AND h.favorite = true")
    int BlogAllFavorite(@Param("blogEntity") BlogEntity blogEntity);

    @Query("SELECT new cn.com.spoc.blogservice.dto.blog.BlogDTO(b.blogTitle, b.author, b.blogContent, b.blogCover) FROM BlogEntity b WHERE b.author = :username")
    List<BlogDTO> getAttentionBlogs(@Param("username") String username);

    @Query("SELECT new cn.com.spoc.blogservice.dto.blog.BlogDTO(h.blogTitle.blogTitle, h.blogTitle.author, h.blogTitle.blogContent, h.blogTitle.blogCover) FROM HotEntity h WHERE h.username = :username AND h.favorite = true")
    List<BlogDTO> getFavoriteBlogs(@Param("username") String username);
}
