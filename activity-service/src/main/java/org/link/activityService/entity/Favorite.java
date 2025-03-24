package org.link.activityService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Favorite {

  private Integer favoriteId;
  private String userId;
  private Integer activityId;
  private Timestamp time;

}
