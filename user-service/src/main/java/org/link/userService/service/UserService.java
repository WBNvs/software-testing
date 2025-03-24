package org.link.userService.service;

import org.link.feign.model.UserModel;
import org.link.userService.dto.StudentInfoDTO;
import org.link.userService.dto.UserInfoDTO;

import java.util.List;

public interface UserService {
    String login(String code);

    List<UserModel> getParticipators(List<String> participatorIdList);

    //feign 查询单个用户对应的userModel
    UserModel getUserModel(String userId);


    //获取用户学生信息
    StudentInfoDTO getStudentInfo(String userId);

    //更新用户学生信息
    StudentInfoDTO updateStudentInfo(String userId, StudentInfoDTO studentInfoDTO);

    //获取用户信息
    UserInfoDTO getUserInfo(String userId);

    //更新用户信息
    UserInfoDTO updateUserInfo(String userId, UserInfoDTO userInfoDTO);



}
