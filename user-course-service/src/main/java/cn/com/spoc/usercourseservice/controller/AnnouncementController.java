package cn.com.spoc.usercourseservice.controller;

import cn.com.spoc.usercourseservice.feign.CourseServiceOpenFeign;
import cn.com.spoc.usercourseservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private CourseServiceOpenFeign courseFeign;
    @Autowired
    private UserServiceOpenFeign userFeign;

    @PostMapping("/create-announcement")
    public ResponseEntity<Map<String, String>> createAnnouncement(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity) && !"assistant".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No Privileges"));
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Cannot Get Username From Token"));
        }
        var announcementRequest = Map.of(
                "username", username,
                "identity", identity,
                "title", request.get("title"),
                "content", request.get("content"),
                "courseId", request.get("courseId")
        );
        return courseFeign.createAnnouncement(announcementRequest);
    }

    @PostMapping("/edit-announcement")
    public ResponseEntity<Map<String, String>> editAnnouncement(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity) && !"assistant".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No Privileges"));
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Cannot Get Username From Token"));
        }
        var announcementRequest = Map.of(
                "username", username,
                "identity", identity,
                "announcementId", request.get("announcementId"),
                "title", request.get("title"),
                "content", request.get("content")
        );
        return courseFeign.editAnnouncement(announcementRequest);
    }

    @PostMapping("/delete-announcement")
    public ResponseEntity<Map<String, String>> deleteAnnouncement(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity) && !"assistant".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No Privileges"));
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Cannot Get Username From Token"));
        }
        var announcementRequest = Map.of(
                "username", username,
                "identity", identity,
                "announcementId", request.get("announcementId")
        );
        return courseFeign.deleteAnnouncement(announcementRequest);
    }
}
