package cn.com.spoc.blogservice.repository;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    @Query("SELECT c FROM CommentEntity c WHERE c.blogEntity = :blogEntity")
    List<CommentEntity> getAllCommentsByBlog(@Param("blogEntity") BlogEntity blogEntity);

    @Query("SELECT c FROM CommentEntity c WHERE c.id = :id")
    CommentEntity getCommentById(@Param("id") Long id);

    @Query("SELECT c FROM CommentEntity c WHERE c.parentComment = :parentComment")
    List<CommentEntity> getAllChildComment(@Param("parentComment") CommentEntity parentComment);

    @Query("SELECT c FROM CommentEntity c WHERE c.user = :user AND c.parentComment IS NOT NULL")
    List<CommentEntity> getCommentByUser(@Param("user") String user);

    @Query("SELECT c FROM CommentEntity c WHERE c.user = :user AND c.parentComment IS NULL ")
    List<CommentEntity> getTopCommentByUser(@Param("user") String user);

    @Query("SELECT c FROM CommentEntity c WHERE c.parentComment.user = :user")
    List<CommentEntity> getCommentToUser(@Param("user") String user);
}
