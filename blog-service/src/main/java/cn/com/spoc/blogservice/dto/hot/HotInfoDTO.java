package cn.com.spoc.blogservice.dto.hot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotInfoDTO {
    private boolean liked;
    private boolean favorite;
}
