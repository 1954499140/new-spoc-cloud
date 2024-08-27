package cn.com.spoc.courseservice.service;

import cn.com.spoc.courseservice.entity.HomeworkEntity;
import cn.com.spoc.courseservice.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepository;

    public void insertHomework(String username, String title, String content, String type, String deadline, String courseId) {
        var homework = new HomeworkEntity(title, content, username, deadline, type, courseId);
        homeworkRepository.save(homework);
    }

    public List<HomeworkEntity> getHomework(String courseId) {
        return homeworkRepository.findHomeworkEntitiesByCourseUUID(courseId);
    }

    public String getCourseIdByHomeworkId(String homeworkId) {
        var id = Integer.parseInt(homeworkId);
        return homeworkRepository.findCourseIdByHomeworkId(id);
    }

    public void editHomework(String homeworkId, String title, String content, String type, String deadline) {
        var id = Integer.parseInt(homeworkId);
        homeworkRepository.updateHomeworkEntityById(title, content, type, deadline, id);
    }

    public void deleteHomework(String homeworkId) {
        var id = Integer.parseInt(homeworkId);
        homeworkRepository.deleteById(id);
    }
}
