package org.link.userService.service.impl;

import org.link.feign.clients.NotificationClient;
import org.link.feign.clients.UserClient;
import org.link.feign.model.NotificationModel;
import org.link.userService.dto.AuthVerifiedDetailDTO;
import org.link.userService.dto.AuthVerifiedListDTO;
import org.link.userService.entity.StudentInfo;
import org.link.userService.entity.User;
import org.link.userService.mapper.StudentInfoMapper;
import org.link.userService.mapper.UserMapper;
import org.link.userService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentInfoMapper studentInfoMapper;

    //获取等待认证的用户列表
    @Override
    public List<AuthVerifiedListDTO> getAuthList() {
        List<AuthVerifiedListDTO> authVerifiedListDTOList = new ArrayList<>();
        List<String> userIdList = studentInfoMapper.findStudentInfoByStatus("PENDING");
        for (String userId : userIdList){
            User user = userMapper.findUser(userId);
            authVerifiedListDTOList.add(new AuthVerifiedListDTO(user));
        }
        return authVerifiedListDTOList;
    }

    //获取已认证的用户列表
    @Override
    public List<AuthVerifiedListDTO> getAuthedList() {
        List<AuthVerifiedListDTO> authVerifiedListDTOList = new ArrayList<>();
        List<String> userIdList = studentInfoMapper.findStudentInfoByStatus("VERIFIED");
        for (String userId : userIdList){
            User user = userMapper.findUser(userId);
            authVerifiedListDTOList.add(new AuthVerifiedListDTO(user));
        }
        return authVerifiedListDTOList;
    }

    @Override
    public AuthVerifiedDetailDTO getAuthDetail(String userId) {
        User user = userMapper.findUser(userId);
        StudentInfo studentInfo = studentInfoMapper.findStudentInfo(userId);
        AuthVerifiedDetailDTO authVerifiedDetailDTO = new AuthVerifiedDetailDTO(user, studentInfo);
        return authVerifiedDetailDTO;
    }

    @Override
    public void handleAuth(String userId, String status) {
        studentInfoMapper.handleAuth(userId, status);
//        new NotificationModel notificationModel();
        //记得加入通知
        return;
    }

    //封禁用户
    @Override
    public void lockUser(String userId){
        userMapper.lockUser(userId);
    }

}
