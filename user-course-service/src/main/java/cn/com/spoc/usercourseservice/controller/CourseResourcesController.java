package cn.com.spoc.usercourseservice.controller;

import cn.com.spoc.usercourseservice.dto.CourseResourcesEntity;
import cn.com.spoc.usercourseservice.feign.CourseServiceOpenFeign;
import cn.com.spoc.usercourseservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course-resources")
public class CourseResourcesController {
    @Autowired
    private CourseServiceOpenFeign courseFeign;
    @Autowired
    private UserServiceOpenFeign userFeign;

    @PostMapping("/upload-resources")
    public ResponseEntity<Map<String, String>> uploadResources(@RequestParam("token") String token, @RequestParam("courseId") String courseId, @RequestParam("file") MultipartFile file) {
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
        return courseFeign.uploadResources(username, identity, courseId, file);
    }

    @PostMapping("/revert")
    public ResponseEntity<Map<String, String>> revert(@RequestBody Map<String, String> request) {
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
        var resourcesRequest = Map.of(
                "username", username,
                "fileId", request.get("fileId")
        );
        return courseFeign.revert(resourcesRequest);
    }

    @PostMapping("/get-course-resources")
    public ResponseEntity<List<CourseResourcesEntity>> getCourseResources(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (identity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        var resourcesRequest = Map.of(
                "courseId", request.get("courseId"),
                "username", username,
                "identity", identity
        );
        return courseFeign.getCourseResources(resourcesRequest);
    }

    @PostMapping("/delete-resource")
    public ResponseEntity<Map<String, String>> deleteResource(@RequestBody Map<String, String> request) {
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
        var resourcesRequest = Map.of(
                "username", username,
                "identity", identity,
                "resourceId", request.get("resourceId")
        );
        return courseFeign.deleteResource(resourcesRequest);
    }
}
