package org.link.activityService.entity;

import lombok.Data;

@Data
public class ActivityImage {
  private Integer imageId;
  private Integer activityId;
  private String photoPath;
}
