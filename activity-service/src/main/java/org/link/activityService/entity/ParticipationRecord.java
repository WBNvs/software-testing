package org.link.activityService.entity;

import lombok.Data;

@Data
public class ParticipationRecord {

  private Integer participateId;
  private String participatorId;
  private Integer activityId;
  private String status;

}
