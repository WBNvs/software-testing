package org.link.notificationService.controller;

import org.link.feign.model.NotificationModel;

import org.link.notificationService.service.NotificationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.link.notificationService.dto.NotificationDetailDTO;
import org.link.notificationService.dto.NotificationDTO;
import org.link.notificationService.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public Result getAllNotification(@RequestHeader String userId){
        List<NotificationDTO> notificationDTOList = notificationService.getNotificationList(userId);
        return Result.success(notificationDTOList);
    }

    @GetMapping("/{notificationId}")
    public Result getNotificationDetail(@PathVariable(value = "notificationId",required = true) Integer notificationId){
        NotificationDetailDTO notificationDetailDTO = notificationService.getNotificationDetail(notificationId);
        return Result.success(notificationDetailDTO);
    }

    //触发生成新通知条件时被其他服务的feign客户端调用的方法
    @PostMapping("/create")
    public void createNotification(@RequestBody NotificationModel notificationModel){
        notificationService.createNotification(notificationModel);

    }


}
