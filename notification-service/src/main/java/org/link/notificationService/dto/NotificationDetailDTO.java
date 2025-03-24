package org.link.notificationService.dto;
import lombok.Data;
import org.link.notificationService.entity.Notifications;

import java.sql.Timestamp;

@Data
public class NotificationDetailDTO {
    private Timestamp time;
    private String type;
    private String content;

    public NotificationDetailDTO(Notifications notification){
        this.content = notification.getContent();
        this.time = notification.getTime();
        this.type = notification.getType();
    }
}
