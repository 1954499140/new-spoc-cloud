package cn.com.spoc.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoDTO {
    private final String username;
    private final String email;
    private final String identity;
    private final String signature;
}
