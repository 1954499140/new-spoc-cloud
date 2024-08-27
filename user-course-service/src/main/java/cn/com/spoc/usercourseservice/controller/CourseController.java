package cn.com.spoc.usercourseservice.controller;

import cn.com.spoc.usercourseservice.dto.CourseInfoDTO;
import cn.com.spoc.usercourseservice.feign.CourseServiceOpenFeign;
import cn.com.spoc.usercourseservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseServiceOpenFeign courseFeign;
    @Autowired
    private UserServiceOpenFeign userFeign;

    @PostMapping("/create-course")
    public ResponseEntity<Map<String, String>> createCourse(
            @RequestParam("file") MultipartFile file,
            @RequestParam("token") String token,
            @RequestParam("name") String name,
            @RequestParam("teacherName") String teacherName,
            @RequestParam("description") String description,
            @RequestParam("primaryClassification") String primaryClassification,
            @RequestParam("secondaryClassification") String secondaryClassification) {
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No Privileges"));
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Cannot Get Username From Token"));
        }
        return courseFeign.createCourse(file, username, name, teacherName, description, primaryClassification, secondaryClassification);
    }

    @PostMapping("/get-my-courses")
    public ResponseEntity<List<CourseInfoDTO>> getMyCourses(@RequestParam("token") String token) {
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return courseFeign.getMyCourses(username, identity);
    }

    @PostMapping("/whether-at-course")
    public ResponseEntity<Boolean> whetherAtCourse(@RequestParam("uuid") String uuid, @RequestParam("token") String token) {
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"teacher".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return courseFeign.whetherAtCourse(uuid, username, identity);
    }
}
