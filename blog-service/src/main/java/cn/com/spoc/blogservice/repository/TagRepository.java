package cn.com.spoc.blogservice.repository;
import cn.com.spoc.blogservice.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, String> {
    @Query("SELECT t FROM TagEntity t WHERE t.tagName = :tagName")
    TagEntity findByTagName(@Param("tagName") String tagName);
}
