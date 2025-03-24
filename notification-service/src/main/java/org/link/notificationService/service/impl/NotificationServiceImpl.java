package org.link.notificationService.service.impl;

import org.link.feign.model.NotificationModel;

import org.link.notificationService.dto.NotificationDTO;
import org.link.notificationService.dto.NotificationDetailDTO;

import org.link.notificationService.service.NotificationService;

import org.link.notificationService.entity.*;
import org.link.notificationService.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{


    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<NotificationDTO> getNotificationList(String userId){

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        List<Notifications> notificationsList = new ArrayList<>();
        notificationsList = notificationMapper.findAllNotification(userId);
        for (Notifications notification : notificationsList){
            notificationDTOList.add(new NotificationDTO(notification));
        }

        return notificationDTOList;
    }

    @Override
    public NotificationDetailDTO getNotificationDetail(Integer notificationId){
        Notifications notification= notificationMapper.findNotification(notificationId);
        NotificationDetailDTO notificationDetailDTO = new NotificationDetailDTO(notification);
        return notificationDetailDTO;
    }

    @Override
    public void createNotification(NotificationModel notificationModel){
//        Notifications notification = new Notifications();
        //对model进行补全，生成id和time 然后放到notification里
        // 设置来自NotificationModel的字段
//        notification.setContent(notificationModel.getContent());
//        notification.setType(notificationModel.getType());
//        notification.setReceiverId(notificationModel.getReceiverId());

        //通知插入
        System.out.println(notificationModel);
        notificationMapper.createNotification(notificationModel);
    }

}
