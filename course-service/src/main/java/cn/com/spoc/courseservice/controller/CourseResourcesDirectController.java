package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.service.CourseResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-resources-direct")
public class CourseResourcesDirectController {
    @Autowired
    private CourseResourcesService courseResourcesService;

    @GetMapping("/{fileId}")
    ResponseEntity<Resource> downloadResource(@PathVariable String fileId) {
        var response = courseResourcesService.download(fileId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return response;
    }
}
