package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.service.AnnouncementService;
import cn.com.spoc.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/announcement-indirect")
public class AnnouncementIndirectController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-announcement")
    public ResponseEntity<Map<String, String>> createAnnouncement(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var title = request.get("title");
        var content = request.get("content");
        var courseUUID = request.get("courseId");
        if (!courseService.whetherAtCourse(courseUUID, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        announcementService.insertAnnouncement(username, courseUUID, title, content);
        return ResponseEntity.ok(Map.of("message", "Create Announcement Successfully"));
    }

    @PostMapping("/edit-announcement")
    public ResponseEntity<Map<String, String>> editAnnouncement(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var announcementId = request.get("announcementId");
        var courseId = announcementService.getCourseIdByAnnouncementId(announcementId);
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        var title = request.get("title");
        var content = request.get("content");
        announcementService.updateAnnouncement(announcementId, title, content);
        return ResponseEntity.ok(Map.of("message", "Update Announcement Successfully"));
    }

    @PostMapping("/delete-announcement")
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var announcementId = request.get("announcementId");
        var courseId = announcementService.getCourseIdByAnnouncementId(announcementId);
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.ok(Map.of("message", "Delete Announcement Successfully"));
    }
}
