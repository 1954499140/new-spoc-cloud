package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.dto.ApproveApplicationsDTO;
import cn.com.spoc.courseservice.dto.CourseMemberDTO;
import cn.com.spoc.courseservice.entity.EnterApplicationEntity;
import cn.com.spoc.courseservice.service.CourseMembersService;
import cn.com.spoc.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course-members-indirect")
public class CourseMembersManagementIndirectController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMembersService courseMembersService;

    @PostMapping("/get-course-members")
    public ResponseEntity<List<CourseMemberDTO>> getCourseMembers(@RequestBody Map<String, String> request) {
        var identity = request.get("identity");
        var courseId = request.get("courseId");
        var username = request.get("username");
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(courseMembersService.getCourseMembersByCourseId(courseId));
    }

    @PostMapping("/remove-members")
    public ResponseEntity<Map<String, String>> removeMembers(@RequestParam("username") String username, @RequestParam("identity") String identity, @RequestParam("courseId") String courseId, @RequestParam("users") List<String> users) {
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not At Course"));
        }
        courseMembersService.removeMembers(courseId, users);
        return ResponseEntity.ok(Map.of("message", "Remove Members Successfully"));
    }

    @PostMapping("/submit-application")
    public ResponseEntity<Map<String, String>> submitApplication(@RequestBody Map<String, String> request) {
        var username = request.get("username");
        var identity = request.get("identity");
        var courseId = request.get("courseId");
        if (courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Already At Course"));
        }
        if (courseMembersService.whetherExistsApplication(courseId, username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "You Have Already Sent Application"));
        }
        var remark = request.get("remark");
        courseMembersService.submitApplication(courseId, identity, username, remark);
        return ResponseEntity.ok(Map.of("message", "Submit Application Successfully"));
    }

    @PostMapping("/get-applications")
    public ResponseEntity<List<EnterApplicationEntity>> getApplications(@RequestBody Map<String, String> request) {
        var identity = request.get("identity");
        var courseId = request.get("courseId");
        var username = request.get("username");
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(courseMembersService.getApplications(courseId));
    }

    @PostMapping("/approve-applications")
    public ResponseEntity<Map<String, String>> approveApplications(@RequestBody ApproveApplicationsDTO request) {
        var username = request.getUsername();
        var identity = request.getIdentity();
        var courseId = request.getCourseId();
        if (!courseService.whetherAtCourse(courseId, username, identity)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not At Course"));
        }
        var applications = request.getApplications();
        var ids = request.getApplicationIds();
        courseMembersService.approveApplications(applications, courseId, ids);
        return ResponseEntity.ok(Map.of("message", "Approve Applications Successfully"));
    }
}
