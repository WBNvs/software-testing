package org.link.activityService.dto;

import lombok.Data;

@Data
public class CreateCommentDTO {
    private Integer activityId;
    private Integer parentFloor;
    private String content;
}
