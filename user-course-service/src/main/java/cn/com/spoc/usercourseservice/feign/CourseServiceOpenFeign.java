package cn.com.spoc.usercourseservice.feign;

import cn.com.spoc.usercourseservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(value = "course-service")
public interface CourseServiceOpenFeign {
    @PostMapping(value = "/course-indirect/create-course", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> createCourse(
            @RequestPart("file") MultipartFile file,
            @RequestPart("teacher") String teacher,
            @RequestPart("name") String name,
            @RequestPart("teacherName") String teacherName,
            @RequestPart("description") String description,
            @RequestPart("primaryClassification") String primaryClassification,
            @RequestPart("secondaryClassification") String secondaryClassification);

    @PostMapping(value = "/course-indirect/get-my-courses", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<CourseInfoDTO>> getMyCourses(@RequestPart("username") String username, @RequestPart("identity") String identity);

    @PostMapping(value = "/course-indirect/whether-at-course", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Boolean> whetherAtCourse(@RequestParam("uuid") String uuid, @RequestParam("username") String username, @RequestParam("identity") String identity);

    @PostMapping(value = "/announcement-indirect/create-announcement")
    ResponseEntity<Map<String, String>> createAnnouncement(@RequestBody Map<String, String> request);

    @PostMapping(value = "/announcement-indirect/edit-announcement")
    ResponseEntity<Map<String, String>> editAnnouncement(@RequestBody Map<String, String> request);

    @PostMapping(value = "/announcement-indirect/delete-announcement")
    ResponseEntity<Map<String, String>> deleteAnnouncement(@RequestBody Map<String, String> request);

    @PostMapping(value = "/homework-indirect/create-homework")
    ResponseEntity<Map<String, String>> createHomework(@RequestBody Map<String, String> request);

    @PostMapping(value = "/homework-indirect/edit-homework")
    ResponseEntity<Map<String, String>> editHomework(@RequestBody Map<String, String> request);

    @PostMapping(value = "/homework-indirect/delete-homework")
    ResponseEntity<Map<String, String>> deleteHomework(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-resources-indirect/upload-resources", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> uploadResources(@RequestPart("username") String username,
                                                        @RequestPart("identity") String identity,
                                                        @RequestPart("courseId") String courseId,
                                                        @RequestPart("file") MultipartFile file);

    @PostMapping(value = "/course-resources-indirect/revert")
    ResponseEntity<Map<String, String>> revert(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-resources-indirect/get-course-resources")
    ResponseEntity<List<CourseResourcesEntity>> getCourseResources(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-resources-indirect/delete-resource")
    ResponseEntity<Map<String, String>> deleteResource(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-members-indirect/get-course-members")
    ResponseEntity<List<CourseMemberDTO>> getCourseMembers(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-members-indirect/remove-members", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, String>> removeMembers(@RequestParam("username") String username,
                                                      @RequestParam("identity") String identity,
                                                      @RequestParam("courseId") String courseId,
                                                      @RequestParam("users") List<String> users);

    @PostMapping(value = "/course-members-indirect/submit-application")
    ResponseEntity<Map<String, String>> submitApplication(@RequestBody Map<String, String> request);

    @PostMapping(value = "course-members-indirect/get-applications")
    ResponseEntity<List<EnterApplicationEntity>> getApplications(@RequestBody Map<String, String> request);

    @PostMapping(value = "/course-members-indirect/approve-applications")
    ResponseEntity<Map<String, String>> approveApplications(@RequestBody ApproveApplicationsDTO request);
}
