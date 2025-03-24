package org.link.activityService.dto;

import java.sql.Timestamp;
import lombok.Data;
import org.link.feign.model.UserModel;

@Data

public class ParticipatorListDTO {
    private String participatorId;
    private String avatar;
    private String nickname;
    private String contact;

    public ParticipatorListDTO(UserModel userModel){
        this.participatorId = userModel.getUserId();
        this.avatar = userModel.getAvatarUrl();
        this.nickname = userModel.getUserName();
        this.contact = userModel.getContactInfo();
    }
}
