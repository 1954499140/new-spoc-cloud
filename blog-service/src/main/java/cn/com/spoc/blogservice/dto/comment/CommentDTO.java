package cn.com.spoc.blogservice.dto.comment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {
    private long id;
    private String authorName;
    private byte[] avatar;
    private String content;
    private String time;
    private List<CommentDTO> child;

    public CommentDTO(long id, String authorName, byte[] avatar, String content, String time) {
        this.id = id;
        this.authorName = authorName;
        this.avatar = avatar;
        this.content = content;
        this.time = time;
        this.child = new ArrayList<>();
    }
}
