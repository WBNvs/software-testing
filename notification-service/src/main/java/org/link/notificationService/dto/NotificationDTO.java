package org.link.notificationService.dto;
import lombok.Data;

import org.link.notificationService.entity.*;
import java.sql.Timestamp;

@Data
public class NotificationDTO {
    private Integer notificationId;
    private Timestamp time;
    private String type;

    public NotificationDTO(Notifications notification){
        this.notificationId = notification.getNotificationId();
        this.time = notification.getTime();
        this.type = notification.getType();
    }
}
