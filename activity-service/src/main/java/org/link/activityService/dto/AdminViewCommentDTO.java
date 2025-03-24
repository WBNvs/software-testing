package org.link.activityService.dto;

import org.link.activityService.entity.Comment;
import org.link.feign.model.UserModel;

import java.sql.Timestamp;
import lombok.Data;


@Data
public class AdminViewCommentDTO {
    private Integer commentId;
    private String userId;
    private Integer parentFloor;
    private String content;
    private Timestamp time;
    private Integer floor;
    private String status;
    private String avatar;
    private String userName;

    public AdminViewCommentDTO(Comment comment, UserModel userModel){
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.parentFloor = comment.getParentFloor();
        this.content = comment.getContent();
        this.time = comment.getTime();
        this.floor = comment.getFloor();
        this.status = comment.getStatus();
        this.avatar = userModel.getAvatarUrl();
        this.userName = userModel.getUserName();
    }
}
