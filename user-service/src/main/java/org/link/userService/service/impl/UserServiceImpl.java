package org.link.userService.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.link.feign.clients.PointClient;
import org.link.feign.model.UserModel;

import org.link.userService.dto.*;
import org.link.userService.entity.*;
import org.link.userService.mapper.*;

import org.link.userService.service.UserService;

import org.link.userService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private PointClient pointClient;

    @Override
    public String login(String code) {
        String appid = "wx8c4851fd79048d73";
        String secret = "d07f6879a4ddfbe7a2f36940d8e2ae07";
        String grantType = "authorization_code";
        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=%s",
                appid, secret, code, grantType);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);
                System.out.println(responseString);
                Pattern pattern = Pattern.compile("\"openid\"\\s*:\\s*\"([^\"]+)\"");
                Matcher matcher = pattern.matcher(responseString);
                if (matcher.find()) {
                    String openid = matcher.group(1);
                    System.out.println(openid);
                    User user = userMapper.findUser(openid);
                    if(user == null){
                        userMapper.addUser(openid);
                        user = new User();
                        user.setUserId(openid);
                        user.setIsBan(false);
                    }
                    StudentInfo studentInfo = studentInfoMapper.findStudentInfo(openid);
                    if (studentInfo == null){
                        studentInfoMapper.addStudentInfo(openid);
                        studentInfo = new StudentInfo();
                        studentInfo.setAuthStatus("UNVERIFIED");
                    }
                    pointClient.initPoint(openid);
                    Map<String,Object> claims = new HashMap<>();
                    claims.put("userId", user.getUserId());
                    claims.put("isBan", user.getIsBan());
                    claims.put("authStatus", studentInfo.getAuthStatus());
                    String token = JwtUtil.genToken(claims);
                    return token;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserModel> getParticipators(List<String> participatorIdList) {
        List<UserModel> userModelList = new ArrayList<>();
        for(String participatorId : participatorIdList){
            User user = userMapper.findUser(participatorId);
            UserModel userModel = new UserModel();
            userModel.setUserId(user.getUserId());
            userModel.setUserName(user.getUserName());
            userModel.setAvatarUrl(user.getAvatarUrl());
            userModel.setContactInfo(user.getContactInfo());
            userModel.setIsBan(user.getIsBan());
            userModelList.add(userModel);
        }
        return userModelList;
    }

    //feign 查询单个用户对应的userModel
    @Override
    public UserModel getUserModel(String userId){

        User user = userMapper.findUser(userId);
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getUserId());
        userModel.setUserName(user.getUserName());
        userModel.setAvatarUrl(user.getAvatarUrl());
        userModel.setContactInfo(user.getContactInfo());
        userModel.setIsBan(user.getIsBan());

        return userModel;
    }


    //获取用户学生认证信息
    @Override
    public StudentInfoDTO getStudentInfo(String userId){
        StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
        StudentInfo studentInfo = studentInfoMapper.findStudentInfo(userId);

        studentInfoDTO.setName(studentInfo.getName());
        studentInfoDTO.setStudentId(studentInfo.getStudentId());
        studentInfoDTO.setCollege(studentInfo.getCollege());
        studentInfoDTO.setAuthStatus(studentInfo.getAuthStatus());

        return studentInfoDTO;
    }

    //更新用户学生认证信息
    public StudentInfoDTO updateStudentInfo(String userId, StudentInfoDTO studentInfoDTO) {
        // 查找用户
        StudentInfo studentInfo = studentInfoMapper.findStudentInfo(userId);
        if (studentInfo == null) {
            throw new RuntimeException("User not found");
        }

        // 更新数据库信息
        studentInfoMapper.updateStudentInfo(userId,studentInfoDTO);

        // 返回更新后的用户信息DTO
        return studentInfoDTO;
    }

    //获取用户信息
    @Override
    public UserInfoDTO getUserInfo(String userId){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        User user = userMapper.findUser(userId);

        userInfoDTO.setUserName(user.getUserName());
        userInfoDTO.setAvatarUrl(user.getAvatarUrl());
        userInfoDTO.setContactInfo(user.getContactInfo());
        userInfoDTO.setIsBan(user.getIsBan());

        return userInfoDTO;
    }

    //更新用户信息
    public UserInfoDTO updateUserInfo(String userId, UserInfoDTO userInfoDTO) {
        // 查找用户
        User user = userMapper.findUser(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 更新数据库信息
        userMapper.updateUser(userId,userInfoDTO);

        // 返回更新后的用户信息DTO
        return userInfoDTO;
    }
}
