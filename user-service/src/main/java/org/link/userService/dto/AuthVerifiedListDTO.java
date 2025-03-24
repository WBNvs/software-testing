package org.link.userService.dto;

import lombok.Data;
import org.link.userService.entity.User;

@Data
public class AuthVerifiedListDTO {
    private String userId;
    private String userName;
    private String avatarUrl;
    private String contactInfo;

    public AuthVerifiedListDTO(User user){
        this.userName = user.getUserName();
        this.userId = user.getUserId();
        this.avatarUrl = user.getAvatarUrl();
        this.contactInfo = user.getContactInfo();
    }
}
