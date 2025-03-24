package org.link.feign.model;

import lombok.Data;

@Data
public class NotificationModel {
    private String receiverId;
    private String content;
    private String type;

    public NotificationModel(String receiverId, String type){
        this.receiverId = receiverId;
        this.type = type;
    }
}
