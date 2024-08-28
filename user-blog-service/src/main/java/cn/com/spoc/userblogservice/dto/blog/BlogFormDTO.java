package cn.com.spoc.userblogservice.dto.blog;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BlogFormDTO {
    private String blogTitle;
    private String blogColumn;
    private String blogContent;
    private String username;
    private List<String> tags;
}
