package cn.com.spoc.usercourseservice.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnterApplicationEntity {
    private int id;
    private String username;
    private String identity;
    private String courseUUID;
    private String remark;
}
