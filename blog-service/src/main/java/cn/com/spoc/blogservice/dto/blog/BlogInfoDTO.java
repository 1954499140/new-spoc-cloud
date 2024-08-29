package cn.com.spoc.blogservice.dto.blog;

import cn.com.spoc.blogservice.dto.hot.HotInfoDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlogInfoDTO {
    String content;
    HotInfoDTO hotInfoDTO;
}
