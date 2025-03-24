package org.link.userService.dto;
import lombok.Data;

@Data
public class UserInfoDTO {
    private String userName;
    private String avatarUrl;
    private String contactInfo;
    private Boolean isBan;
}
