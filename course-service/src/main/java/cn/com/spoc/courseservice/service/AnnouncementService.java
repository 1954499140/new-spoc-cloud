package cn.com.spoc.courseservice.service;

import cn.com.spoc.courseservice.entity.AnnouncementEntity;
import cn.com.spoc.courseservice.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public void insertAnnouncement(String username, String courseUUID, String title, String content) {
        var announcement = new AnnouncementEntity(username, title, content, courseUUID);
        announcementRepository.save(announcement);
    }

    public List<AnnouncementEntity> getAnnouncements(String courseId) {
        return announcementRepository.findAnnouncementEntitiesByCourseUUID(courseId);
    }

    public String getCourseIdByAnnouncementId(String announcementId) {
        var id = Integer.parseInt(announcementId);
        return announcementRepository.findCourseIdByAnnouncementId(id);
    }

    public void updateAnnouncement(String announcementId, String title, String content) {
        var id = Integer.parseInt(announcementId);
        announcementRepository.updateAnnouncementEntityById(id, title, content);
    }

    public void deleteAnnouncement(String announcementId) {
        var id = Integer.parseInt(announcementId);
        announcementRepository.deleteById(id);
    }
}
