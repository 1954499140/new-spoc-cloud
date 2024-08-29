package cn.com.spoc.blogservice.dto.hot;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorInfoDTO {
    private byte[] avatar;
    private String username;
    private long likeNum;
    private int articleNum;
    private int attended;
    private boolean isAttention;

    public AuthorInfoDTO(int articleNum, int attended) {
        this.articleNum = articleNum;
        this.attended = attended;
        this.avatar = null;
        this.username = null;
        this.isAttention = true;
    }
}
