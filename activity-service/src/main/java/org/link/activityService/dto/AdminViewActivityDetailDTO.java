package org.link.activityService.dto;

import org.link.activityService.entity.*;
import org.link.feign.model.UserModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
@Data

public class AdminViewActivityDetailDTO {
    private Integer activityId;
    private String activityName;
    private String organizerId;
    private String organizerAvatar;
    private String category;
    private Timestamp postTime;
    private Timestamp startTime;
    private String description;
    private Integer currentNum;
    private Integer maxNum;
    private Integer likeCount;
    private Integer favoriteCount;
    private String status;
    private List<String> pictures;
    private List<String> participatorAvatars;

    public AdminViewActivityDetailDTO(Activity activity,List<ActivityImage> activityImageList, List<UserModel> userModelList){
        this.pictures = new ArrayList<>();
        this.participatorAvatars = new ArrayList<>();

        this.activityId = activity.getActivityId();
        this.activityName = activity.getActivityName();
        this.organizerId = activity.getOrganizerId();
        //获取发起者头像
        UserModel lastUserModel = userModelList.remove(userModelList.size() - 1);
        this.organizerAvatar = lastUserModel.getAvatarUrl();
        this.category = activity.getCategory();
        this.postTime = activity.getPostTime();
        this.startTime = activity.getStartTime();
        this.description = activity.getDescription();
        this.currentNum = activity.getCurrentNum();
        this.maxNum = activity.getMaxNum();
        this.likeCount = activity.getLikeCount();
        this.favoriteCount = activity.getFavoriteCount();
        this.status = activity.getStatus();
        for(ActivityImage activityImage : activityImageList){
            this.pictures.add(activityImage.getPhotoPath());
        }
        for (UserModel userModel : userModelList){
            this.participatorAvatars.add(userModel.getAvatarUrl());
        }
    }
}
