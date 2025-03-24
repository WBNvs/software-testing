package org.link.activityService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Report {

  private Integer reportId;
  private Integer activityId;
  private String userId;
  private String reason;
  private String status;
  private Timestamp time;

}
