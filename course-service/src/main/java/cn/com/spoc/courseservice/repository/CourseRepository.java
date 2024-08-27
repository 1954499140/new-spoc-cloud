package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.dto.CourseInfoDTO;
import cn.com.spoc.courseservice.dto.CourseBaseInfoDTO;
import cn.com.spoc.courseservice.entity.CourseEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, String> {
    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseBaseInfoDTO(course.uuid, course.name, course.teacherName) FROM CourseEntity course WHERE course.secondaryClassification = :classification")
    List<CourseBaseInfoDTO> findCourseBaseInfoBySecondaryClassification(@Param("classification") String classification, Pageable pageable);

    @Query("SELECT course.cover FROM CourseEntity course WHERE course.uuid = :uuid")
    byte[] findCoverByUUID(@Param("uuid") String uuid);

    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseBaseInfoDTO(course.uuid, course.name, course.teacherName) FROM CourseEntity course WHERE course.cover IS NOT NULL")
    List<CourseBaseInfoDTO> findSomeCourses(Pageable pageable);

    @Query(value = "SELECT courses.uuid, courses.name, courses.teacher_name, courses.primary_classification, courses.secondary_classification, courses.description FROM courses WHERE MATCH(courses.name, courses.teacher_name, courses.description) AGAINST(:keyword IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Map<String, String>> searchCoursesByKeyword(@Param("keyword") String keyword);

    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseInfoDTO(course.uuid, course.name, course.teacherName, course.primaryClassification, course.secondaryClassification, course.description) FROM CourseEntity course WHERE course.secondaryClassification IN :classifications")
    List<CourseInfoDTO> findCoursesByClassifications(@Param("classifications") List<String> classifications);

    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseInfoDTO(course.uuid, course.name, course.teacherName, course.primaryClassification, course.secondaryClassification, course.description) FROM CourseEntity course WHERE course.teacher = :teacher")
    List<CourseInfoDTO> findCoursesByTeacher(@Param("teacher") String teacher);

    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseInfoDTO(course.uuid, course.name, course.teacherName, course.primaryClassification, course.secondaryClassification, course.description) FROM CourseEntity course WHERE course.uuid = :uuid")
    CourseInfoDTO findCourseByUUID(@Param("uuid") String uuid);

    @Query("SELECT course.uuid FROM CourseEntity course WHERE course.uuid = :uuid AND course.teacher = :teacher")
    String whetherCourseBelongsToTeacher(@Param("uuid") String uuid, @Param("teacher") String teacher);
}
