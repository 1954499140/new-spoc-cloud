package cn.com.spoc.courseservice.service;

import cn.com.spoc.courseservice.dto.CourseInfoDTO;
import cn.com.spoc.courseservice.dto.CourseBaseInfoDTO;
import cn.com.spoc.courseservice.entity.CourseEntity;
import cn.com.spoc.courseservice.repository.CourseRepository;
import cn.com.spoc.courseservice.repository.CourseStudentAndAssistantRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseStudentAndAssistantRepository courseStudentAndAssistantRepository;

    public void insertCourse(String uuid, String name, String teacher, String teacherName, String description, String primaryClassification, String secondaryClassification, byte[] cover) {
        var course = new CourseEntity(uuid, name, teacher, teacherName, description, primaryClassification, secondaryClassification, cover);
        courseRepository.save(course);
    }

    public List<CourseBaseInfoDTO> getCoursesBySecondaryClassification(String classification) {
        var pageable = PageRequest.of(0, 5);
        return courseRepository.findCourseBaseInfoBySecondaryClassification(classification, pageable);
    }

    public boolean existsCover(String uuid) {
        return courseRepository.findCoverByUUID(uuid) != null;
    }

    public byte[] getCoverByUUID(String uuid) {
        return courseRepository.findCoverByUUID(uuid);
    }

    public List<CourseBaseInfoDTO> getDisplayCourse() {
        var pageable = PageRequest.of(0, 5);
        return courseRepository.findSomeCourses(pageable);
    }

    @PostConstruct
    public void createFullTextIndex() {
        var checkIndexQuery = "SHOW INDEX FROM courses WHERE Key_name = 'idx_full_text'";
        var createIndexQuery = "ALTER TABLE courses ADD FULLTEXT INDEX idx_full_text (name, teacher_name, description)";

        var indexExists = (Boolean) jdbcTemplate.query(checkIndexQuery, (ResultSetExtractor<Object>) ResultSet::next);
        if (Boolean.FALSE.equals(indexExists)) {
            jdbcTemplate.execute(createIndexQuery);
        }
    }

    public List<CourseInfoDTO> searchCoursesByKeyword(String keyword) {
        var results = courseRepository.searchCoursesByKeyword(keyword);
        var list = new ArrayList<CourseInfoDTO>();
        for (var result : results) {
            var uuid = result.get("uuid");
            var name = result.get("name");
            var teacherName = result.get("teacherName");
            var primaryClassification = result.get("primary_classification");
            var secondaryClassification = result.get("secondary_classification");
            var description = result.get("description");
            list.add(new CourseInfoDTO(uuid, name, teacherName, primaryClassification, secondaryClassification, description));
        }
        return list;
    }

    public List<CourseInfoDTO> getCoursesByClassifications(List<String> classifications) {
        return courseRepository.findCoursesByClassifications(classifications);
    }

    public List<CourseInfoDTO> getCoursesByTeacher(String teacher) {
        return courseRepository.findCoursesByTeacher(teacher);
    }

    public List<CourseInfoDTO> getCoursesByStudentOrAssistant(String username) {
        return courseStudentAndAssistantRepository.getCoursesByUsername(username);
    }

    public CourseInfoDTO getCourseByUUID(String uuid) {
        return courseRepository.findCourseByUUID(uuid);
    }

    public boolean whetherAtCourse(String uuid, String username, String identity) {
        if (identity.equals("teacher")) {
            return courseRepository.whetherCourseBelongsToTeacher(uuid, username) != null;
        }
        return courseStudentAndAssistantRepository.whetherStudentAtCourse(uuid, username) != null;
    }
}
