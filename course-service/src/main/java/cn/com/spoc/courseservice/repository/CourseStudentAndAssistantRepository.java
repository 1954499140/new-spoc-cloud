package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.dto.CourseInfoDTO;
import cn.com.spoc.courseservice.dto.CourseMemberDTO;
import cn.com.spoc.courseservice.entity.CourseStudentAndAssistantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseStudentAndAssistantRepository extends JpaRepository<CourseStudentAndAssistantEntity, Integer> {
    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseInfoDTO(course1.uuid, course1.name, course1.teacherName, course1.primaryClassification, course1.secondaryClassification, course1.description) FROM CourseEntity course1 JOIN CourseStudentAndAssistantEntity course2 ON course1.uuid = course2.courseUUID WHERE course2.username = :username")
    List<CourseInfoDTO> getCoursesByUsername(@Param("username") String username);

    @Query("SELECT course.courseUUID FROM CourseStudentAndAssistantEntity course WHERE course.courseUUID = :uuid AND course.username = :username")
    String whetherStudentAtCourse(@Param("uuid") String uuid, @Param("username") String username);

    @Query("SELECT new cn.com.spoc.courseservice.dto.CourseMemberDTO(member.username, member.identity) FROM CourseStudentAndAssistantEntity member WHERE member.courseUUID = :courseId")
    List<CourseMemberDTO> findMembersByCourseId(@Param("courseId") String courseId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CourseStudentAndAssistantEntity member WHERE member.username IN :users AND member.courseUUID = :courseId")
    void removeMembersByUsername(@Param("users") List<String> users, @Param("courseId") String courseId);
}
