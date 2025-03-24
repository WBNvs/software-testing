package org.link.userService.controller;

import org.link.userService.dto.*;

import org.link.feign.model.UserModel;
import org.link.userService.service.UserService;
import org.link.userService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params){
        String code = params.get("code");
        String token = userService.login(code);
        if (token == null)
        {
            return Result.error("登录失败");
        }
        System.out.println(token);
        return Result.success(token);
    }

    //feign 根据一个用户id列表返回其基本信息列表？
    @GetMapping("/participators")
    public List<UserModel> getParticipators(@RequestParam("participatorIdList") List<String> participatorIdList){
        List<UserModel> userModelList = userService.getParticipators(participatorIdList);
        return userModelList;
    }

    //feign 查询单个用户对应的userModel
    @GetMapping("/userInfo/userModel")
    UserModel getUserModel(@RequestHeader String userId){
        UserModel userModel = userService.getUserModel(userId);
        return userModel;
    }

    //测试接口
    @PostMapping("/test")
    public Result test(@RequestHeader String userId, @RequestHeader String isBan, @RequestHeader String authStatus){
        System.out.println(userId);
        System.out.println(isBan);
        System.out.println(authStatus);
        return Result.success();
    }

    //获取用户学生信息
    @GetMapping("/studentInfo")
    public Result getStudentInfo(@RequestHeader String userId){
        StudentInfoDTO studentInfoDTO = userService.getStudentInfo(userId);
        return Result.success(studentInfoDTO);
    }

    //认证用户学生信息
    @PutMapping("/studentInfo")
    public Result updateStudentInfo(
            @RequestHeader String userId,
            @RequestBody StudentInfoDTO studentInfoDTO) {
        return Result.success(userService.updateStudentInfo(userId, studentInfoDTO));
    }

    //获取用户信息
    @GetMapping("/userInfo")
    public Result getUserInfo(@RequestHeader String userId){
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        return Result.success(userInfoDTO);
    }

    //更新用户信息
    @PutMapping("/userInfo")
    public Result updateUserInfo(
            @RequestHeader String userId,
            @RequestBody UserInfoDTO userInfoDTO) {
        return Result.success(userService.updateUserInfo(userId, userInfoDTO));
    }


}
