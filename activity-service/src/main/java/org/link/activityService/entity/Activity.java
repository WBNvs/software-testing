package org.link.activityService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Activity {

  private Integer activityId;
  private String organizerId;
  private String activityName;
  private String category;
  private Timestamp postTime;
  private Timestamp startTime;
  private String description;
  private Integer maxNum;
  private Integer likeCount;
  private Integer favoriteCount;
  private String status;
  private Integer currentNum;


}
