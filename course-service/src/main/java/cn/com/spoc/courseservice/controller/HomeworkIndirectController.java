package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.service.CourseService;
import cn.com.spoc.courseservice.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/homework-indirect")
public class HomeworkIndirectController {
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-homework")
    public ResponseEntity<Map<String, String>> createHomework(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var deadline = request.get("deadline");
        var title = request.get("title");
        var content = request.get("content");
        var type = request.get("type");
        var courseId = request.get("courseId");
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        homeworkService.insertHomework(username, title, content, type, deadline, courseId);
        return ResponseEntity.ok(Map.of("message", "Create Homework Successfully"));
    }

    @PostMapping("/edit-homework")
    public ResponseEntity<Map<String, String>> editHomework(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var homeworkId = request.get("homeworkId");
        var courseId = homeworkService.getCourseIdByHomeworkId(homeworkId);
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        var deadline = request.get("deadline");
        var type = request.get("type");
        var title = request.get("title");
        var content = request.get("content");
        homeworkService.editHomework(homeworkId, title, content, type, deadline);
        return ResponseEntity.ok(Map.of("message", "Update Homework Successfully"));
    }

    @PostMapping("/delete-homework")
    public ResponseEntity<Map<String, String>> deleteHomework(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var homeworkId = request.get("homeworkId");
        var courseId = homeworkService.getCourseIdByHomeworkId(homeworkId);
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not At Course"));
        }
        homeworkService.deleteHomework(homeworkId);
        return ResponseEntity.ok(Map.of("message", "Delete Homework Successfully"));
    }
}
