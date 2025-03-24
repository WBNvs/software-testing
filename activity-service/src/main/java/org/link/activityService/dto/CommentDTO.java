package org.link.activityService.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import org.link.activityService.entity.Comment;
import org.link.feign.model.UserModel;

@Data
public class CommentDTO {
    private Integer commentId;
    private String userId;
    private Integer parentFloor;
    private String content;
    private Timestamp time;
    private Integer floor;
    private String status;
    private String avatar;
    private String userName;
    private boolean ifMine;

    public CommentDTO(String userId, Comment comment, UserModel userModel){
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.parentFloor = comment.getParentFloor();
        this.content = comment.getContent();
        this.time = comment.getTime();
        this.floor = comment.getFloor();
        this.status = comment.getStatus();
        this.avatar = userModel.getAvatarUrl();
        this.userName = userModel.getUserName();
        if (userId.equals(comment.getUserId())){
            ifMine = true;
        }else {
            ifMine = false;
        }
    }
}
