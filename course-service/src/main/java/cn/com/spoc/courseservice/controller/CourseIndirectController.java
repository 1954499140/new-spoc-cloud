package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.dto.CourseInfoDTO;
import cn.com.spoc.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/course-indirect")
public class CourseIndirectController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/create-course")
    public ResponseEntity<Map<String, String>> createCourse(
            @RequestParam("file") MultipartFile file,
            @RequestParam("teacher") String teacher,
            @RequestParam("name") String name,
            @RequestParam("teacherName") String teacherName,
            @RequestParam("description") String description,
            @RequestParam("primaryClassification") String primaryClassification,
            @RequestParam("secondaryClassification") String secondaryClassification) {
        var uuid = UUID.randomUUID().toString();
        try {
            if (file.isEmpty()) {
                courseService.insertCourse(uuid, name, teacher, teacherName, description, primaryClassification, secondaryClassification, null);
            } else {
                courseService.insertCourse(uuid, name, teacher, teacherName, description, primaryClassification, secondaryClassification, file.getBytes());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "File Get Failed"));
        }
        return ResponseEntity.ok(Map.of("message", "Create Successfully"));
    }

    @PostMapping("/get-my-courses")
    public ResponseEntity<List<CourseInfoDTO>> getMyCourses(@RequestParam("username") String username, @RequestParam("identity") String identity) {
        if (identity.equals("teacher")) {
            return ResponseEntity.ok(courseService.getCoursesByTeacher(username));
        }
        return ResponseEntity.ok(courseService.getCoursesByStudentOrAssistant(username));
    }

    @PostMapping("/whether-at-course")
    public ResponseEntity<Boolean> whetherAtCourse(@RequestParam("uuid") String uuid, @RequestParam("username") String username, @RequestParam("identity") String identity) {
        var result = courseService.whetherAtCourse(uuid, username, identity);
        return ResponseEntity.ok(result);
    }
}
