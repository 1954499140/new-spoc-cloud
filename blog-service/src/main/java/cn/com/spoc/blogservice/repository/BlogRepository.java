package cn.com.spoc.blogservice.repository;

import cn.com.spoc.blogservice.dto.blog.BlogDTO;
import cn.com.spoc.blogservice.entity.BlogEntity;
import cn.com.spoc.blogservice.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, String> {
    @Query("SELECT b FROM BlogEntity b WHERE b.blogTitle = :blogTitle")
    BlogEntity findBlogEntitiesByBlogTitle(@Param("blogTitle") String blogTitle);

    @Query("SELECT new cn.com.spoc.blogservice.dto.blog.BlogDTO(b.blogTitle, b.author, b.blogContent, b.blogCover) FROM BlogEntity b")
    List<BlogDTO> getAllBlogs();

    @Query("SELECT new cn.com.spoc.blogservice.dto.blog.BlogDTO(b.blogTitle, b.author, b.blogContent, b.blogCover) FROM BlogEntity b WHERE b.blogColumn = :columnEntity")
    List<BlogDTO> getByColumn(@Param("columnEntity") ColumnEntity columnEntity);

    @Query("SELECT blog FROM BlogEntity blog WHERE blog.author = :author")
    List<BlogEntity> getBlogEntitiesByAuthor(@Param("author") String author);

    @Query("SELECT COUNT(blog) FROM BlogEntity blog WHERE blog.author = :author")
    int countBlogsByAuthor(@Param("author") String author);

    @Query("SELECT blog FROM BlogEntity blog WHERE blog.author = :author")
    List<BlogEntity> getBlogsByAuthor(@Param("author") String author);
}
