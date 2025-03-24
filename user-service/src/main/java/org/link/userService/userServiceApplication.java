package org.link.userService;


import org.link.feign.clients.PointClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {PointClient.class})
@SpringBootApplication
public class userServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(userServiceApplication.class, args);
    }
}
