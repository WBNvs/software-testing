package org.link.activityService;

import org.link.feign.clients.NotificationClient;
import org.link.feign.clients.PointClient;
import org.link.feign.clients.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {UserClient.class, PointClient.class, NotificationClient.class})
@SpringBootApplication
public class activityServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(activityServiceApplication.class, args);
    }
}
