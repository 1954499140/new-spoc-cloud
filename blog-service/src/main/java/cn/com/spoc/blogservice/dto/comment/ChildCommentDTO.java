package cn.com.spoc.blogservice.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChildCommentDTO {
    private String time;
    private String parentComment;
    private String comment;
    private String authorName;
}
