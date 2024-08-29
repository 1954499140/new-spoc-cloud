package cn.com.spoc.blogservice.dto.blog;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
public class BlogDTO {
    private String blogTitle;
    private String author;
    private int likeNum;
    private int favoriteNum;
    private String content;
    private byte[] cover;

    public BlogDTO(String blogTitle, String author, String content, byte[] cover) {
        this.blogTitle = blogTitle;
        this.author = author;
        this.content = content;
        this.cover = cover;
        this.likeNum = 0;
        this.favoriteNum = 0;
    }
}
