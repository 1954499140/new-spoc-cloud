package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.entity.CourseResourcesEntity;
import cn.com.spoc.courseservice.service.CourseResourcesService;
import cn.com.spoc.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/course-resources-indirect")
public class CourseResourcesIndirectController {
    @Autowired
    private CourseResourcesService courseResourcesService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/upload-resources")
    public ResponseEntity<Map<String, String>> uploadResources(@RequestParam("username") String username, @RequestParam("identity") String identity, @RequestParam("courseId") String courseId, @RequestParam("file") MultipartFile file) {
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        var fileId = UUID.randomUUID().toString();
        var result = courseResourcesService.storeFile(file, username, courseId, fileId);
        if (result) {
            return ResponseEntity.ok(Map.of("uuid", fileId));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Upload Failed"));
    }

    @PostMapping("/revert")
    public ResponseEntity<Map<String, String>> revert(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var fileId = request.get("fileId");
        var result = courseResourcesService.revert(fileId, username);
        if (result) {
            return ResponseEntity.ok(Map.of("message", "Revert Successfully"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Revert Failed"));
    }

    @PostMapping("/get-course-resources")
    public ResponseEntity<List<CourseResourcesEntity>> getCourseResources(@RequestBody Map<String, String> request) {
        var courseId = request.get("courseId");
        var username = request.get("username");
        var identity = request.get("identity");
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(courseResourcesService.getCourseResources(courseId));
    }

    @PostMapping("/delete-resource")
    public ResponseEntity<Map<String, String>> deleteResource(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var resourceId = request.get("resourceId");
        var courseId = courseResourcesService.getCourseIdByResourceId(resourceId);
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        var result = courseResourcesService.deleteResource(resourceId);
        if (result) {
            return ResponseEntity.ok(Map.of("message", "Delete Resource Successfully"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Delete Resource Failed"));
    }
}
