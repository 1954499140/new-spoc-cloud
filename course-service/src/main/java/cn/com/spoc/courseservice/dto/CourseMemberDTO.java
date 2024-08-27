package cn.com.spoc.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseMemberDTO {
    private final String username;
    private final String identity;
}
