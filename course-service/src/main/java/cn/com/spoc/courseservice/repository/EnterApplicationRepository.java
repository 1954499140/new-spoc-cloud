package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.entity.EnterApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnterApplicationRepository extends JpaRepository<EnterApplicationEntity, Integer> {
    @Query("SELECT application.id FROM EnterApplicationEntity application WHERE application.username = :username AND application.courseUUID = :courseId")
    Integer findIdByUsernameAndCourseId(@Param("username") String username, @Param("courseId") String courseId);

    @Query("SELECT application FROM EnterApplicationEntity application WHERE application.courseUUID = :courseId")
    List<EnterApplicationEntity> findEnterApplicationEntitiesByCourseUUID(@Param("courseId") String courseId);
}
