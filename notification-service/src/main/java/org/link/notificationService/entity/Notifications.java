package org.link.notificationService.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notifications {

  private Integer notificationId;
  private String receiverId;
  private String content;
  private Timestamp time;
  private String type;
}
