package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.entity.HomeworkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<HomeworkEntity, Integer> {
    @Query("SELECT homework FROM HomeworkEntity homework WHERE homework.courseUUID = :courseId")
    List<HomeworkEntity> findHomeworkEntitiesByCourseUUID(@Param("courseId") String courseId);

    @Query("SELECT homework.courseUUID FROM HomeworkEntity homework WHERE homework.id = :homeworkId")
    String findCourseIdByHomeworkId(@Param("homeworkId") Integer homeworkId);

    @Transactional
    @Modifying
    @Query("UPDATE HomeworkEntity homework SET homework.title = :title, homework.content = :content, homework.type = :type, homework.deadline = :deadline WHERE homework.id = :homeworkId")
    void updateHomeworkEntityById(@Param("title") String title, @Param("content") String content, @Param("type") String type, @Param("deadline") String deadline, @Param("homeworkId") Integer homeworkId);
}
