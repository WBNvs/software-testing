package org.link.userService.controller;

import org.link.userService.dto.AuthVerifiedDetailDTO;
import org.link.userService.dto.AuthVerifiedListDTO;
import org.link.userService.service.AdminService;
import org.link.userService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //显示所有等待认证的用户的列表
    @GetMapping("/authList/unverified")
    public Result getAuthList(){
        List<AuthVerifiedListDTO> authVerifiedListDTOList = adminService.getAuthList();
        return Result.success(authVerifiedListDTOList);
    }

    //显示所有已经完成认证的用户的列表
    @GetMapping("/authList/verified")
    public Result getAuthedList(){
        List<AuthVerifiedListDTO> authVerifiedListDTOList = adminService.getAuthedList();
        return Result.success(authVerifiedListDTOList);
    }

    //用于点击某一待审核信息的详情页面显示
    @GetMapping("/authDetail/{userId}")
    public Result getAuthDetail(@PathVariable String userId){
        AuthVerifiedDetailDTO authVerifiedDetailDTO = adminService.getAuthDetail(userId);
        return Result.success(authVerifiedDetailDTO);
    }

    //处理审核信息
    @PutMapping("/auth/{userId}")
    public Result handleAuth(@PathVariable String userId, @RequestParam String status){
        adminService.handleAuth(userId, status);
        return Result.success();
    }

    //封禁用户
    @PutMapping("/lockUser/{userId}")
    public Result lockUser(@PathVariable String userId){
        adminService.lockUser(userId);
        return Result.success();
    }

}
