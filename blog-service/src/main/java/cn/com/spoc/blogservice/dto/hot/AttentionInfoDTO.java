package cn.com.spoc.blogservice.dto.hot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttentionInfoDTO {
    private byte[] avatar;
    private String username;
}
