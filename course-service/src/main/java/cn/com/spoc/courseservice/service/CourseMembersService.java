package cn.com.spoc.courseservice.service;

import cn.com.spoc.courseservice.dto.CourseMemberDTO;
import cn.com.spoc.courseservice.entity.CourseStudentAndAssistantEntity;
import cn.com.spoc.courseservice.entity.EnterApplicationEntity;
import cn.com.spoc.courseservice.repository.CourseStudentAndAssistantRepository;
import cn.com.spoc.courseservice.repository.EnterApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseMembersService {
    @Autowired
    private CourseStudentAndAssistantRepository courseMembersRepository;
    @Autowired
    private EnterApplicationRepository enterApplicationRepository;

    public List<CourseMemberDTO> getCourseMembersByCourseId(String courseId) {
        return courseMembersRepository.findMembersByCourseId(courseId);
    }

    public void removeMembers(String courseId, List<String> users) {
        courseMembersRepository.removeMembersByUsername(users, courseId);
    }

    public boolean whetherExistsApplication(String courseId, String username) {
        return enterApplicationRepository.findIdByUsernameAndCourseId(username, courseId) != null;
    }

    public void submitApplication(String courseId, String identity, String username, String remark) {
        var application = new EnterApplicationEntity(username, identity, courseId, remark);
        enterApplicationRepository.save(application);
    }

    public List<EnterApplicationEntity> getApplications(String courseId) {
        return  enterApplicationRepository.findEnterApplicationEntitiesByCourseUUID(courseId);
    }

    public void approveApplications(List<CourseMemberDTO> applications, String courseId, List<Integer> applicationIds) {
        for (var application : applications) {
            courseMembersRepository.save(new CourseStudentAndAssistantEntity(courseId, application.getUsername(), application.getIdentity()));
        }
        enterApplicationRepository.deleteAllById(applicationIds);
    }
}
