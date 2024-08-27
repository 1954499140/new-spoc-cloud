package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.entity.AnnouncementEntity;
import cn.com.spoc.courseservice.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/announcement-direct")
public class AnnouncementDirectController {
    @Autowired
    private AnnouncementService announcementService;
    @PostMapping("/get-announcements")
    public ResponseEntity<List<AnnouncementEntity>> getAnnouncements(@RequestParam("courseId") String courseId) {
        return ResponseEntity.ok(announcementService.getAnnouncements(courseId));
    }
}
