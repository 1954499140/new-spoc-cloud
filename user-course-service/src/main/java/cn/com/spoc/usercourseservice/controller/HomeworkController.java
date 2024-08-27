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
@RequestMapping("/homework")
public class HomeworkController {
    @Autowired
    private CourseServiceOpenFeign courseFeign;
    @Autowired
    private UserServiceOpenFeign userFeign;

    @PostMapping("/create-homework")
    public ResponseEntity<Map<String, String>> createHomework(@RequestBody Map<String, String> request) {
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
        var homeworkRequest = Map.of(
                "username", username,
                "identity", identity,
                "deadline", request.get("deadline"),
                "title", request.get("title"),
                "content", request.get("content"),
                "type", request.get("type"),
                "courseId", request.get("courseId")
        );
        return courseFeign.createHomework(homeworkRequest);
    }

    @PostMapping("/edit-homework")
    public ResponseEntity<Map<String, String>> editHomework(@RequestBody Map<String, String> request) {
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
        var homeworkRequest = Map.of(
                "username", username,
                "identity", identity,
                "deadline", request.get("deadline"),
                "title", request.get("title"),
                "content", request.get("content"),
                "type", request.get("type"),
                "homeworkId", request.get("homeworkId")
        );
        return courseFeign.editHomework(homeworkRequest);
    }

    @PostMapping("/delete-homework")
    public ResponseEntity<Map<String, String>> deleteHomework(@RequestBody Map<String, String> request) {
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
        var homeworkRequest = Map.of(
                "username", username,
                "identity", identity,
                "homeworkId", request.get("homeworkId")
        );
        return courseFeign.deleteHomework(homeworkRequest);
    }
}
