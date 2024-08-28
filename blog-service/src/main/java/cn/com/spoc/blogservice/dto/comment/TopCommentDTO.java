package cn.com.spoc.blogservice.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCommentDTO {
    private String authorName;
    private String comment;
    private String blogTitle;
    private String time;
}
