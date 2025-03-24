package org.link.activityService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {

  private Integer commentId;
  private Integer activityId;
  private String userId;
  private Integer parentFloor;
  private String content;
  private Timestamp time;
  private Integer floor;
  private String status;

}
