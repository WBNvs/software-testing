package org.link.pointService.controller;

import org.link.pointService.dto.*;

import org.link.pointService.service.PointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.link.pointService.utils.Result;
//import org.link.feign.model.NotificationModel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;


    //初始化用户对应的积分表记录
    @PostMapping("/init")
    public Integer initPoint(@RequestParam("userId") String userId){
        Integer point = pointService.initPoint(userId);
        return point;
    }

    //后端内部触发 更改积分&增加积分变动记录  仅返回更新后的用户积分量
    @PostMapping("/change")
    public Integer updatePoint(@RequestHeader String userId,@RequestParam("point") Integer point){
        return pointService.updatePointsAndTransaction(userId,point);
    }



    //获取某用户的积分变动记录
    @GetMapping("/pointTransaction")
    public Result getAllPointTransaction(@RequestHeader String userId){
        List<PointTransactionDTO> pointTransactionDTOList = pointService.getAllAPointTransaction(userId);
        if (pointTransactionDTOList == null){
            return Result.error("积分交易记录为空");
        }
        return Result.success(pointTransactionDTOList);
    }

    //获取某用户积分数
    @GetMapping("/userPoint")
    public Result getPoint(@RequestHeader String userId){
        PointDTO pointDTO = pointService.getUserPoint(userId);
        return Result.success(pointDTO);
    }

    //用户购买积分


}
