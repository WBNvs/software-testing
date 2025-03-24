package org.link.activityService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Likes {

  private Integer likeId;
  private String userId;
  private Integer activityId;
  private Timestamp time;

}
