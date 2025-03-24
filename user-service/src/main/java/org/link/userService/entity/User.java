package org.link.userService.entity;


import lombok.Data;

@Data
public class User {

  private String userId;
  private String userName;
  private String avatarUrl;
  private String contactInfo;
  private Boolean isBan;

}
