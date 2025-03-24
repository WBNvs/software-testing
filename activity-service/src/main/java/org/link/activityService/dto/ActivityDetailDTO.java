package org.link.activityService.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.link.activityService.entity.*;
import org.link.feign.model.UserModel;

@Data
public class ActivityDetailDTO {
    private Integer activityId;
    private String activityName;
    private String organizerId;
    private String organizerAvatar;
    private String organizerName;
    private String category;
    private Timestamp postTime;
    private Timestamp startTime;
    private String description;
    private Integer currentNum;
    private Integer maxNum;
    private Integer likeCount;
    private Integer favoriteCount;
    private String status;
    private Boolean isFavored;
    private Boolean isLiked;
    private String participantStatus;
    private List<String> pictures;
    private List<String> participatorAvatars;

    public ActivityDetailDTO(Activity activity, Favorite favorite, Likes likes, ParticipationRecord participationRecord, List<ActivityImage> activityImageList, List<UserModel> userModelList){
        this.pictures = new ArrayList<>();
        this.participatorAvatars = new ArrayList<>();

        this.activityId = activity.getActivityId();
        this.activityName = activity.getActivityName();
        this.organizerId = activity.getOrganizerId();
        //获取发起者头像
        UserModel lastUserModel = userModelList.remove(userModelList.size() - 1);
        this.organizerAvatar = lastUserModel.getAvatarUrl();
        this.organizerName = lastUserModel.getUserName();
        this.category = activity.getCategory();
        this.postTime = activity.getPostTime();
        this.startTime = activity.getStartTime();
        this.description = activity.getDescription();
        this.currentNum = activity.getCurrentNum();
        this.maxNum = activity.getMaxNum();
        this.likeCount = activity.getLikeCount();
        this.favoriteCount = activity.getFavoriteCount();
        this.status = activity.getStatus();
        if(favorite == null){
            this.isFavored = false;
        }else {
            this.isFavored = true;
        }
        if (likes == null){
            this.isLiked = false;
        }else {
            this.isLiked = true;
        }
        if (participationRecord == null){
            this.participantStatus = "unJoin";
        }else {
            this.participantStatus = participationRecord.getStatus();
        }
        for(ActivityImage activityImage : activityImageList){
            this.pictures.add(activityImage.getPhotoPath());
        }
        for (UserModel userModel : userModelList){
            this.participatorAvatars.add(userModel.getAvatarUrl());
        }
    }
}
