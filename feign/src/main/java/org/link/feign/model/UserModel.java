package org.link.feign.model;


import lombok.Data;

@Data
public class UserModel {

  private String userId;
  private String userName;
  private String avatarUrl;
  private String contactInfo;
  private Boolean isBan;

}
