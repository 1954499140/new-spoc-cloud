package cn.com.spoc.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApproveApplicationsDTO {
    private String username;
    private String identity;
    private String courseId;
    private List<CourseMemberDTO> applications;
    private List<Integer> applicationIds;
}
