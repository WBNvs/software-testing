package org.link.feign.clients;

import org.link.feign.model.NotificationModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notification-service")
public interface NotificationClient {
    @PostMapping("/notification/create")
    Integer createNotification(NotificationModel notificationModel);

}
