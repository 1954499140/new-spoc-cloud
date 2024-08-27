package cn.com.spoc.usercourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ApproveApplicationsDTO {
    private String username;
    private String identity;
    private String courseId;
    private List<CourseMemberDTO> applications;
    private List<Integer> applicationIds;
}
