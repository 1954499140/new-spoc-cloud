package cn.com.spoc.courseservice.controller;

import cn.com.spoc.courseservice.entity.HomeworkEntity;
import cn.com.spoc.courseservice.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/homework-direct")
public class HomeworkDirectController {
    @Autowired
    private HomeworkService homeworkService;
    @PostMapping("/get-homework")
    public ResponseEntity<List<HomeworkEntity>> getHomework(@RequestParam("courseId") String courseId) {
        return ResponseEntity.ok(homeworkService.getHomework(courseId));
    }
}
