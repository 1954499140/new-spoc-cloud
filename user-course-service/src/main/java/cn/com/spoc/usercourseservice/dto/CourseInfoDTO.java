package cn.com.spoc.usercourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseInfoDTO {
    private final String uuid;
    private final String name;
    private final String teacherName;
    private final String primaryClassification;
    private final String secondaryClassification;
    private final String description;
}
