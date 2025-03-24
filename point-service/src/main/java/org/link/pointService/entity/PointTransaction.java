package org.link.pointService.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class PointTransaction {

  private Integer transactionId;
  private String userId;
  private Integer points;
  private Timestamp time;


}
