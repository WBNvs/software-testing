package org.link.activityService.dto;

import java.sql.Timestamp;

import lombok.Data;
import org.link.activityService.entity.Activity;
import org.link.activityService.entity.Likes;

@Data
public class ActivityListDTO {
    private Integer activityId;
    private String activityName;
    private String category;
    private Timestamp postTime;
    private Timestamp startTime;
    private Integer currentNum;
    private Integer maxNum;
    private Integer likeCount;
    private String status;
    private Boolean ifLiked;

    public ActivityListDTO(Activity activity, Likes likes){
        this.activityId = activity.getActivityId();
        this.activityName = activity.getActivityName();
        this.category = activity.getCategory();
        this.postTime = activity.getPostTime();
        this.startTime = activity.getStartTime();
        this.currentNum = activity.getCurrentNum();
        this.maxNum = activity.getMaxNum();
        this.likeCount = activity.getLikeCount();
        this.status = activity.getStatus();
        if (likes == null){
            ifLiked = false;
        }else {
            ifLiked = true;
        }
    }
}
