package org.link.activityService.dto;
import org.link.activityService.entity.Report;
import java.sql.Timestamp;

import lombok.Data;


@Data
public class PendingReportListDTO {
    private Integer reportId;
    private Integer activityId;
    private String userId;
    private String reason;
    private Timestamp time;

    public PendingReportListDTO(Report report){
        this.reportId = report.getReportId();
        this.activityId = report.getActivityId();
        this.userId = report.getUserId();
        this.reason = report.getReason();
        this.time = report.getTime();
    }
}


