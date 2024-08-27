package cn.com.spoc.courseservice.repository;

import cn.com.spoc.courseservice.entity.AnnouncementEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Integer> {
    @Query("SELECT announcement FROM AnnouncementEntity announcement WHERE announcement.courseUUID = :courseId")
    List<AnnouncementEntity> findAnnouncementEntitiesByCourseUUID(@Param("courseId") String courseId);

    @Query("SELECT announcement.courseUUID FROM AnnouncementEntity announcement WHERE announcement.id = :announcementId")
    String findCourseIdByAnnouncementId(@Param("announcementId") Integer announcementId);

    @Transactional
    @Modifying
    @Query("UPDATE AnnouncementEntity announcement SET announcement.title = :title, announcement.content = :content WHERE announcement.id = :announcementId")
    void updateAnnouncementEntityById(@Param("announcementId") Integer announcementId, @Param("title") String title, @Param("content") String content);
}
