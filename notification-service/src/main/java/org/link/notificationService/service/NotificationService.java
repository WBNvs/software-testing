package org.link.notificationService.service;

import org.link.feign.model.NotificationModel;

import org.link.notificationService.dto.NotificationDTO;
import org.link.notificationService.dto.NotificationDetailDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationList(String userId);

    NotificationDetailDTO getNotificationDetail(Integer notificationId);

    void createNotification(NotificationModel notificationModel);

}
