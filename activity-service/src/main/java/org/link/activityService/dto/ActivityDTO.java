package org.link.activityService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
public class ActivityDTO {
    private String activityId;
    private String activityName;
    private String organizerId;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startTime;
    private String description;
    private Integer maxNum;
    private String status;
    private List<String> pictures;
}
