package cn.com.spoc.usercourseservice.controller;

import cn.com.spoc.usercourseservice.dto.ApproveApplicationsDTO;
import cn.com.spoc.usercourseservice.dto.CourseMemberDTO;
import cn.com.spoc.usercourseservice.dto.EnterApplicationEntity;
import cn.com.spoc.usercourseservice.dto.PreApproveApplicationsDTO;
import cn.com.spoc.usercourseservice.feign.CourseServiceOpenFeign;
import cn.com.spoc.usercourseservice.feign.UserServiceOpenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course-members")
public class CourseMembersManagementController {
    @Autowired
    private UserServiceOpenFeign userFeign;
    @Autowired
    private CourseServiceOpenFeign courseFeign;

    @PostMapping("/get-course-members")
    public ResponseEntity<List<CourseMemberDTO>> getCourseMembers(@RequestBody Map<String, String> request) {
        var token = request.get("token");
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
        var membersRequest = Map.of(
                "username", username,
                "identity", identity,
                "courseId", request.get("courseId")
        );
        return courseFeign.getCourseMembers(membersRequest);
    }

    @PostMapping("/remove-members")
    public ResponseEntity<Map<String, String>> removeMembers(@RequestParam("token") String token, @RequestParam("courseId") String courseId, @RequestParam("users") List<String> users) {
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
        return courseFeign.removeMembers(username, identity, courseId, users);
    }

    @PostMapping("/submit-application")
    public ResponseEntity<Map<String, String>> submitApplication(@RequestBody Map<String, String> request) {
        var token = request.get("token");
        if (userFeign.tokenInvalid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid Token"));
        }
        var identity = userFeign.getIdentityFromToken(token);
        if (!"student".equals(identity) && !"assistant".equals(identity)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No Privileges"));
        }
        var username = userFeign.getUsernameFromToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Cannot Get Username From Token"));
        }
        var membersRequest = Map.of(
                "username", username,
                "identity", identity,
                "courseId", request.get("courseId"),
                "remark", request.get("remark")
        );
        return courseFeign.submitApplication(membersRequest);
    }

    @PostMapping("/get-applications")
    public ResponseEntity<List<EnterApplicationEntity>> getApplications(@RequestBody Map<String, String> request) {
        var token = request.get("token");
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
        var membersRequest = Map.of(
                "username", username,
                "identity", identity,
                "courseId", request.get("courseId")
        );
        return courseFeign.getApplications(membersRequest);
    }

    @PostMapping("/approve-applications")
    public ResponseEntity<Map<String, String>> approveApplications(@RequestBody PreApproveApplicationsDTO request) {
        var token = request.getToken();
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
        var membersRequest = new ApproveApplicationsDTO(username, identity, request.getCourseId(), request.getApplications(), request.getApplicationIds());
        return courseFeign.approveApplications(membersRequest);
    }
}
