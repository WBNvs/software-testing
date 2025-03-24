package org.link.notificationService.mapper;

import org.apache.ibatis.annotations.*;
import org.link.feign.model.NotificationModel;
import org.link.notificationService.entity.Notifications;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select * from notifications where receiver_id = #{userId}")
    List<Notifications> findAllNotification(@Param("userId")String userId);

    @Select("select * from notifications where notification_id = #{notificationId}")
    Notifications findNotification(@Param("notificationId")Integer notificationId);

    @Insert("INSERT INTO notifications(receiver_id, content,type) VALUES(#{notification.receiverId}, #{notification.content}, #{notification.type})")
    void createNotification(@Param("notification") NotificationModel notification);

}
