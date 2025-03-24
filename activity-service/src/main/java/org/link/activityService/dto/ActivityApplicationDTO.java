package org.link.activityService.dto;

import lombok.Data;
import org.link.feign.model.UserModel;

@Data
public class ActivityApplicationDTO {
    private String applicantId;
    private String avatar;
    private String nickname;

    public ActivityApplicationDTO(UserModel userModel){
        this.applicantId = userModel.getUserId();
        this.avatar = userModel.getAvatarUrl();
        this.nickname = userModel.getUserName();
    }
}
