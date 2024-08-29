package cn.com.spoc.userblogservice.dto.comment;

import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    private long id;
    private String authorName;
    private byte[] avatar;
    private String content;
    private String time;
    private List<CommentDTO> child;
}
