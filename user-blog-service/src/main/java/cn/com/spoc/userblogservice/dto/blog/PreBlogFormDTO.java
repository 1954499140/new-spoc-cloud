package cn.com.spoc.userblogservice.dto.blog;

import lombok.Getter;

import java.util.List;

@Getter
public class PreBlogFormDTO {
    private String blogTitle;
    private String blogColumn;
    private String blogContent;
    private String token;
    private List<String> tags;
}
