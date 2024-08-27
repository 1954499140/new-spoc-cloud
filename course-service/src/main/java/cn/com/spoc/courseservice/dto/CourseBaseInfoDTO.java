package cn.com.spoc.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseBaseInfoDTO {
    private final String uuid;
    private final String name;
    private final String teacherName;
}
