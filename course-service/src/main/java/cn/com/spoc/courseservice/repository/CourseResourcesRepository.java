package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.entity.CourseResourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseResourcesRepository extends JpaRepository<CourseResourcesEntity, String> {
    @Query("SELECT resource.title FROM CourseResourcesEntity resource WHERE resource.uuid = :fileId AND resource.username = :username")
    String findTitleByFileIdAndUsername(@Param("fileId") String fileId, @Param("username") String username);

    @Query("SELECT resource.title FROM CourseResourcesEntity resource WHERE resource.uuid = :fileId")
    String findTitleByFileId(@Param("fileId") String fileId);

    @Query("SELECT resource FROM CourseResourcesEntity resource WHERE resource.courseId = :courseId")
    List<CourseResourcesEntity> findCourseResourcesEntitiesByCourseId(@Param("courseId") String courseId);

    @Query("SELECT resource.courseId FROM CourseResourcesEntity resource WHERE resource.uuid = :resourceId")
    String findCourseIdByResourceId(@Param("resourceId") String resourceId);
}
