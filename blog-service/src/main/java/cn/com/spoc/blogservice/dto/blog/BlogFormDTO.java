package cn.com.spoc.blogservice.dto.blog;

import lombok.Data;

import java.util.List;

@Data
public class BlogFormDTO {
    private String blogTitle;
    private String blogColumn;
    private String blogContent;
    private String token;
    private List<String> tags;
}
