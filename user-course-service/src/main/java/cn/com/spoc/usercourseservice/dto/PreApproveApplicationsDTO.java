package cn.com.spoc.usercourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class PreApproveApplicationsDTO {
    private String token;
    private String courseId;
    private List<CourseMemberDTO> applications;
    private List<Integer> applicationIds;
}
