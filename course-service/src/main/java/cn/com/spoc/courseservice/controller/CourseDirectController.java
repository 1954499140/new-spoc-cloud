package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.dto.CourseBaseInfoDTO;
import cn.com.spoc.courseservice.dto.CourseInfoDTO;
import cn.com.spoc.courseservice.service.CourseService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course-direct")
public class CourseDirectController {
    @Autowired
    private CourseService courseService;
    @Value("${application.course-cover.base-url}")
    private String courseCoverBaseUrl;
    @Value("${application.course-cover.default-url}")
    private String defaultCourseCoverUrl;

    @PostMapping("/get-courses-by-secondary-classification")
    public ResponseEntity<List<CourseBaseInfoDTO>> getCoursesBySecondaryClassification(@RequestBody Map<String, String> request) {
        var classification = request.get("classification");
        return ResponseEntity.ok(courseService.getCoursesBySecondaryClassification(classification));
    }

    @PostMapping("/get-course-cover-url")
    public ResponseEntity<Map<String, String>> getCourseCoverUrl(@RequestBody Map<String, String> request) {
        var uuid = request.get("uuid");
        if (courseService.existsCover(uuid)) {
            return ResponseEntity.ok(Map.of("url", courseCoverBaseUrl + uuid));
        }
        return ResponseEntity.ok(Map.of("url", defaultCourseCoverUrl));
    }

    @GetMapping("/cover/{uuid}")
    public void getCourseCover(@PathVariable String uuid, HttpServletResponse response) {
        var cover = courseService.getCoverByUUID(uuid);
        try {
            if (cover == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                response.setContentType("image/jpeg");
                response.setContentLength(cover.length);
                response.getOutputStream().write(cover);
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/get-display-courses")
    public ResponseEntity<List<CourseBaseInfoDTO>> getDisplayCourses() {
        return ResponseEntity.ok(courseService.getDisplayCourse());
    }

    @PostMapping("/search-courses")
    public ResponseEntity<List<CourseInfoDTO>> searchCourses(@RequestBody Map<String, String> request) {
        var keyword = request.get("keyword");
        return ResponseEntity.ok(courseService.searchCoursesByKeyword(keyword));
    }

    @PostMapping("/get-courses-by-classifications")
    public ResponseEntity<List<CourseInfoDTO>> getCoursesByClassifications(@RequestParam("classifications") List<String> classifications) {
        if (classifications.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(courseService.getCoursesByClassifications(classifications));
    }

    @PostMapping("/get-course-by-uuid")
    public ResponseEntity<CourseInfoDTO> getCourseByUUID(@RequestParam("uuid") String uuid) {
        var course = courseService.getCourseByUUID(uuid);
        return ResponseEntity.ok(course);
    }
}
